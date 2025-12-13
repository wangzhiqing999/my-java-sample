
## 测试用的数据库脚本

### 测试的表.

```
CREATE TABLE test_log (
	log_id serial4 NOT NULL, -- 日志流水号，自增主键
	log_text text NULL, -- 日志文本信息
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 日志创建的时间，默认为当前时间
	CONSTRAINT test_log_pkey PRIMARY KEY (log_id)
);
COMMENT ON TABLE public.test_log IS '测试日志的表';

-- Column comments

COMMENT ON COLUMN public.test_log.log_id IS '日志流水号，自增主键';
COMMENT ON COLUMN public.test_log.log_text IS '日志文本信息';
COMMENT ON COLUMN public.test_log.created_at IS '日志创建的时间，默认为当前时间';
```


### 无参数、无返回值.

```
CREATE OR REPLACE FUNCTION public.test_nop_nor()
 RETURNS void
 LANGUAGE plpgsql
AS $function$
BEGIN
  INSERT INTO test_log(
    log_text, created_at
  ) VALUES(
    '无参数，无返回', CURRENT_TIMESTAMP
  );
END;
$function$
;
```

### 有参数，无返回值

```
CREATE OR REPLACE FUNCTION public.test_havep_nor(p_log_text character varying)
 RETURNS void
 LANGUAGE plpgsql
AS $function$
BEGIN
  INSERT INTO test_log(
    log_text, created_at
  ) VALUES(
    '有参数，无返回' || p_log_text, CURRENT_TIMESTAMP
  );
END;
$function$
;
```

### 有参数，有返回值

```
CREATE OR REPLACE FUNCTION public.test_havep_haver(p_log_text character varying)
 RETURNS bigint
 LANGUAGE plpgsql
AS $function$
DECLARE
  new_id bigint;
BEGIN
  INSERT INTO test_log(
    log_text, created_at
  ) VALUES(
    '有参数，有返回：' || p_log_text, CURRENT_TIMESTAMP
  )
  RETURNING log_id INTO new_id;
  RETURN new_id;
END;
$function$
;
```



### 有参数，有返回值，返回值的类型是 json.

```
CREATE OR REPLACE FUNCTION public.test_havep_haverj(p_log_text character varying)
 RETURNS json
 LANGUAGE plpgsql
AS $function$
DECLARE
  new_id bigint;
BEGIN
  INSERT INTO test_log(
    log_text, created_at
  ) VALUES(
    '有参数，返回JSON：' || p_log_text, CURRENT_TIMESTAMP
  )
  RETURNING log_id INTO new_id;
  
  RETURN json_build_object (
	'code', 0,
	'msg',	'success',
	'id',	new_id);
END;
$function$
;
```


### 有参数，有返回值，参数与返回值的类型都是 json.
```
CREATE OR REPLACE FUNCTION public.test_havepj_haverj(p_log_data json)
 RETURNS json
 LANGUAGE plpgsql
AS $function$
DECLARE
  new_id bigint;
  v_log_text varchar;
BEGIN

  v_log_text := p_log_data->>'log_text';

  INSERT INTO test_log(
    log_text, created_at
  ) VALUES(
    '参数JSON，返回JSON：' || v_log_text, CURRENT_TIMESTAMP
  )
  RETURNING log_id INTO new_id;
  
  RETURN json_build_object (
	'code', 0,
	'msg',	'success',
	'id',	new_id);
END;
$function$
;
```



### 测试的 配置表的功能.

```

CREATE TABLE common_config (
	config_code		VARCHAR(32) 	NOT NULL,
	config_desc		VARCHAR(256),
	config_value	JSON,
	
	ctime timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	utime timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT common_config_pkey PRIMARY KEY (config_code)
);

COMMENT ON TABLE common_config IS '通用配置表';

COMMENT ON COLUMN common_config.config_code IS '配置代码';
COMMENT ON COLUMN common_config.config_desc IS '配置描述';
COMMENT ON COLUMN common_config.config_value IS '配置值';

COMMENT ON COLUMN common_config.ctime IS '配置创建时间';
COMMENT ON COLUMN common_config.utime IS '配置更新时间';

```

```

CREATE OR REPLACE FUNCTION fn_save_config(p_code VARCHAR(32), p_value JSON)
RETURNS void
LANGUAGE plpgsql
AS 
$function$
BEGIN
	INSERT INTO common_config (
		config_code, config_desc, config_value
	) VALUES (
		p_code, '-', p_value
	)
	ON CONFLICT (config_code)
	DO UPDATE 
	SET 
		config_value = p_value, 
		utime=CURRENT_TIMESTAMP;
END;
$function$
;


CREATE OR REPLACE FUNCTION fn_get_config(p_code VARCHAR(32))
RETURNS JSON
LANGUAGE plpgsql
AS 
$function$
DECLARE
	v_result	JSON;
BEGIN
	SELECT
		config_value INTO v_result
	FROM
		common_config
	WHERE
		config_code = p_code;		
	RETURN v_result;
END;
$function$
;

```



### 测试方式
http GET
http://localhost:8080/test/get





## AI 生成 ECC 加密、解密Java代码 的提示词


我使用下面的命令，生成ecc私钥.  
```  
openssl ecparam -name secp256k1 -genkey -noout -out priv_key_s.pem  
```  
  
内容如下  
-----BEGIN EC PRIVATE KEY-----  
MHQCAQEEIFUmcJTzQ59/1Am2RS1wdYmPvyVTW9vTBIfXkBYRE/VSoAcGBSuBBAAK  
oUQDQgAEMRSbFUksXVsUefZA8KbUbF8YnaaBFazEKPiJ09yf6cVlnf73YgYlWzV0  
4RD/KoRUbnqx2S0YFpcKrM+PPMTLwg==  
-----END EC PRIVATE KEY-----  
  
  
  
我还运行了下面的命令，导出ecc公钥.  
```  
openssl ec -in priv_key_s.pem -pubout -out pub_key_s.pem  
```  
  
内容如下  
-----BEGIN PUBLIC KEY-----  
MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEMRSbFUksXVsUefZA8KbUbF8YnaaBFazE  
KPiJ09yf6cVlnf73YgYlWzV04RD/KoRUbnqx2S0YFpcKrM+PPMTLwg==  
-----END PUBLIC KEY-----  
  
  
  
能不能给我生成 读取 ECC 公钥、私钥的 java 代码。  
以及用公钥、私钥，完成 ECC 加密、解密的 java 代码。


### 测试方式

#### 测试加密
http POST
http://localhost:8080/test/encrypt

Body 的地方，填写：
{"log_text":"这是使用公钥加密的数据"} 

结果返回：
AFgwVjAQBgcqhkjOPQIBBgUrgQQACgNCAAT6sembKQ/RLhvi4xZX4m5g0U+f7EGQuh5g8YCPm1Hq5YCady7DADGbTHcHwfrB259yoJjZeDNhVPKDj5g+mSrmAAzQYUK4Zx/KvV9aDn2mj1Jfe6UOyjIw8E3EDk+Ebc5X2AnERvbbJ0mb/mQETAO5ChYHx6alq9Sj2fpLfjzBBmEZVypwLE6hg7PofHpuMQ==


#### 测试解密
http POST

http://localhost:8080/test/decrypt

Body 的地方，填写前面的 加密返回的结果。

结果返回：
{"log_text":"这是使用公钥加密的数据"}



#### 测试解密后，调用存储过程.
http POST 

http://localhost:8080/test/savelog

Body 的地方，填写前面的 加密返回的结果。

预期的结果返回：success
查看数据库，看看数据是否存储到表当中.




## 定时任务的配置

### Application

添加
```
@EnableScheduling // 开启定时任务
```

### Task

使用
```
@Scheduled(cron = "0 * * * * ?")
```
注意：测试的时候，是每分钟调用一次， 实际使用中，需要时情况，进行修改.


