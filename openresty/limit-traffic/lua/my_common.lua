local _M = { _VERSION = '1.0.0' }

-- 获取客户端真实ip地址
function _M.getClientIp()
	local headers=ngx.req.get_headers()
        local clientIp=headers["X_Forwarded_For"] or headers["X-Real-IP"] or ngx.var.remote_addr or "127.0.0.1"
	return clientIp;
end

return _M
