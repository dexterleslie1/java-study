local _M = { _VERSION = '1.0.0' }

-- 获取客户端真实ip地址
function _M.getClientIp()
	local headers=ngx.req.get_headers()
        local clientIp=headers["X_Forwarded_For"] or headers["X-Real-IP"] or ngx.var.remote_addr or "127.0.0.1"
	return clientIp;
end

function _M.log(level, keyPrefix, message)
	local sharedRef = ngx.shared;
	local latestLogTime = sharedRef.logFrequencyStore:get(keyPrefix .. "#latestLogTime");
	if not latestLogTime then
		local timeNow = ngx.now()*1000;
		sharedRef.logFrequencyStore:set(keyPrefix .. "#latestLogTime", timeNow);
		latestLogTime = timeNow-2000;
	end

	local newValue, error, forcible = sharedRef.logFrequencyStore:incr(keyPrefix .. "#count", 1, 0);
	
	local timeNow = ngx.now()*1000;
    	local timeInterval = timeNow - latestLogTime;
	if timeInterval>=1000 then
		ngx.log(level, "[" .. newValue .. "]条," .. message);
		local timeNow = ngx.now()*1000;
		sharedRef.logFrequencyStore:set(keyPrefix .. "#latestLogTime", timeNow);
		sharedRef.logFrequencyStore:set(keyPrefix .. "#count", nil);
	end
end

return _M
