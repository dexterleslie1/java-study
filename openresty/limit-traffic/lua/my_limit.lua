-- 使用lua脚本操作x-forwarded-for
-- https://github.com/openresty/lua-nginx-module/issues/801
ngx.req.clear_header("x-forwarded-for");
ngx.req.set_header("x-forwarded-for", ngx.var.remote_addr);

-- 获取客户端真实ip地址
local common = require("my_common");
local clientIp = common.getClientIp();

local enableLimitConn = true;
local enableLimitReq = true;
local maxCommittedCount = 110;
local maxCommittedCountAll = 2000;
local maxBlockedTimeout = 3600;

-- 客户端在黑名单中
local blocked = ngx.shared.recordStore:get("blocked#" .. clientIp);
if blocked then
        common.log(ngx.WARN, "blocked#" .. clientIp, "客户端" .. clientIp .. "被列入黑名单，拒绝请求");
	return ngx.exit(503);
end

-- 120秒内犯规次数达到110次把ip地址添加到黑名单中
local committedCount = ngx.shared.recordStore:get("committedCount#" .. clientIp);
local committedCountAll = ngx.shared.recordStore:get("committedCountAll#" .. clientIp);
if (committedCount and committedCount>=maxCommittedCount) or (committedCountAll and committedCountAll>=maxCommittedCountAll) then
	local firstCommittedTime = ngx.shared.recordStore:get("firstCommittedTime#" .. clientIp);
	local timeNow = ngx.time()*1000;
	local timeInterval = timeNow-firstCommittedTime;
	if timeInterval<=120000 then
		ngx.shared.recordStore:set("blocked#" .. clientIp, true, maxBlockedTimeout);

		ngx.shared.recordStore:delete("committedCount#" .. clientIp);
                ngx.shared.recordStore:delete("firstCommittedTime#" .. clientIp);
                ngx.shared.recordStore:delete("latestCommittedTime#" .. clientIp);
		
		ngx.log(ngx.WARN, "客户端" .. clientIp .. "两分钟内犯规committedCount=" .. committedCount .. ",committedCountAll=" .. committedCountAll .. "被列入黑名单");
		return ngx.exit(503);
	else
		ngx.shared.recordStore:delete("committedCount#" .. clientIp);
                ngx.shared.recordStore:delete("firstCommittedTime#" .. clientIp);
                ngx.shared.recordStore:delete("latestCommittedTime#" .. clientIp);
	end
end

if enableLimitConn then
-- 并发连接数限制
-- well, we could put the require() and new() calls in our own Lua
-- modules to save overhead. here we put them below just for
-- convenience.

local limit_conn = require "resty.limit.conn"

-- limit the requests under 200 concurrent requests (normally just
-- incoming connections unless protocols like SPDY is used) with
-- a burst of 100 extra concurrent requests, that is, we delay
-- requests under 300 concurrent connections and above 200
-- connections, and reject any new requests exceeding 300
-- connections.
-- also, we assume a default request time of 0.5 sec, which can be
-- dynamically adjusted by the leaving() call in log_by_lua below.
local lim, err = limit_conn.new("my_limit_conn_store", 5, 1, 1)
if not lim then
	ngx.log(ngx.ERR, "failed to instantiate a resty.limit.conn object: ", err)
	return ngx.exit(500)
end

-- the following call must be per-request.
-- here we use the remote (IP) address as the limiting key
local delay, err = lim:incoming(clientIp, true)
if not delay then
	if err == "rejected" then
	
		-- 记录第一次犯规时间		
		local firstCommittedTime = ngx.shared.recordStore:get("firstCommittedTime#" .. clientIp);
		if not firstCommittedTime then
			local timeNow = ngx.now()*1000;
			ngx.shared.recordStore:set("firstCommittedTime#" .. clientIp, timeNow, 120);
			ngx.log(ngx.WARN, "客户端" .. clientIp .. "超出连接数限制记录第一次犯规时间");
		end

		-- 距离上次犯规时间超过1秒则增加当前犯规次数
		local latestCommittedTime = ngx.shared.recordStore:get("latestCommittedTime#" .. clientIp);
		if not latestCommittedTime then
			local timeNow = ngx.now()*1000-2000;
			ngx.shared.recordStore:set("latestCommittedTime#" .. clientIp, timeNow);
			latestCommittedTime = timeNow;
			ngx.log(ngx.WARN, "客户端" .. clientIp .. "超出连接数限制第一次犯规并首次记录最近犯规时间");
		end
    		
		local timeNow = ngx.now()*1000;
		local timeInterval = timeNow - latestCommittedTime;
		if timeInterval>=1000 then
			-- 犯规次数增加1
                	ngx.shared.recordStore:incr("committedCount#" .. clientIp, 1, 0);
			ngx.shared.recordStore:set("latestCommittedTime#" .. clientIp, timeNow);
			ngx.log(ngx.WARN, "客户端" .. clientIp .. "超出连接数限制，犯规次数+1");
		end
		ngx.shared.recordStore:incr("committedCountAll#" .. clientIp, 1, 0);

		return ngx.exit(503)
	end
	ngx.log(ngx.ERR, "failed to limit req: ", err)
	return ngx.exit(500)
end

if lim:is_committed() then
	local ctx = ngx.ctx
	ctx.limit_conn = lim
	ctx.limit_conn_key = clientIp
	ctx.limit_conn_delay = delay
end

-- the 2nd return value holds the current concurrency level
-- for the specified key.
local conn = err

if delay >= 0.001 then
	-- the request exceeding the 200 connections ratio but below
	-- 300 connections, so
	-- we intentionally delay it here a bit to conform to the
	-- 200 connection limit.
	-- ngx.log(ngx.WARN, "delaying")
        -- ngx.log(ngx.WARN, "客户端连接数超出服务器限制进入burst延迟队列,delay=" .. delay);
	ngx.sleep(delay)
end
end

if enableLimitReq then
-- 请求速率限制
-- well, we could put the require() and new() calls in our own Lua
-- modules to save overhead. here we put them below just for
-- convenience.

local limit_req = require "resty.limit.req"

-- limit the requests under 200 req/sec with a burst of 100 req/sec,
-- that is, we delay requests under 300 req/sec and above 200
-- req/sec, and reject any requests exceeding 300 req/sec.
local lim, err = limit_req.new("my_limit_req_store", 5, 10)
if not lim then
	ngx.log(ngx.ERR, "failed to instantiate a resty.limit.req object: ", err)
	return ngx.exit(500)
end

-- the following call must be per-request.
-- here we use the remote (IP) address as the limiting key
local delay, err = lim:incoming(clientIp, true)
if not delay then
	if err == "rejected" then
	
		-- 记录第一次犯规时间
                local firstCommittedTime = ngx.shared.recordStore:get("firstCommittedTime#" .. clientIp);
                if not firstCommittedTime then
                        local timeNow = ngx.now()*1000;
                        ngx.shared.recordStore:set("firstCommittedTime#" .. clientIp, timeNow, 120);
                        ngx.log(ngx.WARN, "客户端" .. clientIp .. "超出请求频率限制记录第一次犯规时间");
                end

                -- 距离上次犯规时间超过1秒则增加当前犯规次数
                local latestCommittedTime = ngx.shared.recordStore:get("latestCommittedTime#" .. clientIp);
                if not latestCommittedTime then
                        local timeNow = ngx.now()*1000-2000;
                        ngx.shared.recordStore:set("latestCommittedTime#" .. clientIp, timeNow);
                        latestCommittedTime = timeNow;
                        ngx.log(ngx.WARN, "客户端" .. clientIp .. "超出请求频率限制第一次犯规并首次记录最近犯规时间");
                end

                local timeNow = ngx.now()*1000;
                local timeInterval = timeNow - latestCommittedTime;
                if timeInterval>=1000 then
                        -- 犯规次数增加1
                        ngx.shared.recordStore:incr("committedCount#" .. clientIp, 1, 0);
                        ngx.shared.recordStore:set("latestCommittedTime#" .. clientIp, timeNow);
                        ngx.log(ngx.WARN, "客户端" .. clientIp .. "超出请求频率限制，犯规次数+1");
                end
		ngx.shared.recordStore:incr("committedCountAll#" .. clientIp, 1, 0);

		return ngx.exit(503)
	end
	ngx.log(ngx.ERR, "failed to limit req: ", err)
	return ngx.exit(500)
end

if delay >= 0.001 then
	-- the 2nd return value holds  the number of excess requests
	-- per second for the specified key. for example, number 31
	-- means the current request rate is at 231 req/sec for the
	-- specified key.
	local excess = err

	-- the request exceeding the 200 req/sec but below 300 req/sec,
	-- so we intentionally delay it here a bit to conform to the
	-- 200 req/sec rate.
	-- ngx.log(ngx.WARN, "客户端请求频率超出服务器限制进入到burst队列，delay=" .. delay);
	ngx.sleep(delay)
end
end
