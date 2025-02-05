-- Lua Script: Compare and Update Location if Count is Greater
-- KEYS[1]: Redis key (location identifier)
-- ARGV[1]: Latitude
-- ARGV[2]: Longitude
-- ARGV[3]: TTL (Time-to-Live in seconds)
-- ARGV[4]: Count

local current = redis.call('GET', KEYS[1])

if current then
    local currentData = cjson.decode(current)
    if tonumber(ARGV[4]) <= currentData.count then
        return false -- Do not update if the current count is greater or equal
    end
end

local newData = cjson.encode({
    latitude = tonumber(ARGV[1]),
    longitude = tonumber(ARGV[2]),
    count = tonumber(ARGV[4])
})

redis.call('SET', KEYS[1], newData, 'EX', ARGV[3])
return true -- Successfully updated
