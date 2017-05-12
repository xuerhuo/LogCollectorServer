# LogCollectorServer
- usage: java -jar LogCollectorServer \
--inputype redis \
--outputype elasticsearch \
--redis_host 127.0.0.1 \
--redis_password pass \
--redis_port 6379 \
--logrediskey key \
--els_host 192.168.109.130 \
--els_port 9300 \
--thread 5 \
--els_index \
xishui
--els_type
log
