package com.my.work.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.ECKey;
import java.util.Date;
import java.util.HashMap;


public class AppleClientSecret {





    // 下面这段字符串的生成机制
    // 先生成私钥
    // openssl ecparam -genkey -name prime256v1 -noout -out private-key.pem

    // 将PKCS1私钥转换为PKCS8(该格式一般Java调用)
    // openssl pkcs8 -topk8 -inform pem -in private-key.pem -outform pem -nocrypt -out private-key-new.pem
    // 复制其中的内容，到下面这里.
    private static final String PRIVATE_KEY_256 = "-----BEGIN PRIVATE KEY-----\n" +
            "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgrxaBSY/EjRlKKsF/\n" +
            "0bqZ1l32ZvX+05WK8ovnmrOylvWhRANCAASkU3d2WuLGj+m6Wl4DkIM4W5mX77zJ\n" +
            "dIzltGAKk1J5I6EpcIVrcswQamvzHK7/X2YDn+ci+b2+am6rBN2wqrSk\n" +
            "-----END PRIVATE KEY-----";



    // clientid、keyid、teamid 是在苹果网站那里，上传了 公钥之后，生成的。
    // 使用私钥生成公钥
    // openssl ec -in private-key.pem -pubout -out public-key.pem
    private static String client_id = "xxx";
    private static String team_id = "xxx";
    private static String key_id = "xxx";





    private static String aud = "https://appleid.apple.com";
    private static String alg = "ES256";






    /**
     * Create a Client Secret
     *
     * @return client secret
     */
    public static String createClientSecret(String privateKeyString) throws Exception {
        Algorithm algorithm = Algorithm.ECDSA256((ECKey) PemUtils.readPrivateKeyFromString(privateKeyString, "EC"));

        return JWT.create()
                .withIssuer(team_id)
                .withAudience(aud)
                .withHeader(new HashMap() {{
                    put("alg", alg);
                    put("kid", key_id);
                }})
                .withSubject(client_id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400 * 180 * 1000L))
                .sign(algorithm);
    }


    public static void main(String[] args) {

        System.out.println("client_id:" + client_id);
        System.out.println("  team_id:" + team_id);
        System.out.println("   key_id:" + key_id);

        try {
            String clientSecret = createClientSecret(PRIVATE_KEY_256);
            System.out.println("clientSecret 建议保存，有效期可设置最长 180 天");
            System.out.println(clientSecret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
