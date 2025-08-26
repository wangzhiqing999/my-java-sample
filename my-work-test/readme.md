
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

测试加密：
http POST
http://localhost:8080/test/encrypt

Body 的地方，填写：
测试使用公钥加密数据

结果返回：
AFgwVjAQBgcqhkjOPQIBBgUrgQQACgNCAAQz5vJchBGT2zzIcAZ/7vpVBeXJMS6W1mvjIz65U2EcK7XHQROfxB9PxfdU6Qe0vTDVVH1cqgBhNHtqbP4f9Io7AAySZGP2kDKyy00X3khigz19WfvVbxVcERfG5OHztIw70HFYgq+Z9fZpIjWJle1kT+TugnLun5iO8Ki0


测试解密：
http POST
http://localhost:8080/test/decrypt

Body 的地方，填写前面的 加密返回的结果。

结果返回：
测试使用公钥加密数据


