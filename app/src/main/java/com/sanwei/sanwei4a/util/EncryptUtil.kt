package com.sanwei.sanwei4a.util

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec


object EncryptUtil {
    fun md5(origin: String): String? {
        try {
            // 得到一个信息摘要器
            val digest = MessageDigest.getInstance("md5")
            val result = digest.digest(origin.toByteArray())
            val buffer = StringBuffer()
            // 把每一个byte 做一个与运算 0xff;
            for (b in result) {
                // 与运算
                val number = b.toInt()and 0xff// 加盐
                val str = Integer.toHexString(number)
                if (str.length == 1) {
                    buffer.append("0")
                }
                buffer.append(str)
            }
            // 标准的md5加密后的结果
            return buffer.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }

    }

    private val ALGORITHMSTR = "AES/ECB/PKCS5Padding"//"算法/模式/补码方式"

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    fun SHA256(strText: String): String? {
        return SHA(strText, "SHA-256")
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    fun SHA512(strText: String): String? {
        return SHA(strText, "SHA-512")
    }

    /**
     * 字符串 SHA 加密
     *
     * @param strSourceText
     * @return
     */
    private fun SHA(strText: String?, strType: String): String? {
        // 返回值
        var strResult: String? = null

        // 是否是有效字符串
        if (strText != null && strText.isNotEmpty()) {
            try {
                // SHA 加密开始
                // 创建加密对象 并传入加密类型
                val messageDigest = MessageDigest.getInstance(strType)
                // 传入要加密的字符串
                messageDigest.update(strText.toByteArray())
                // 得到 byte 类型结果
                val byteBuffer = messageDigest.digest()

                // 将byte 转换为 string
                val strHexString = StringBuffer()
                // 遍历byte buffer
                for (i in byteBuffer.indices) {
                    val hex = Integer.toHexString(0xff and byteBuffer[i].toInt())
                    if (hex.length == 1) {
                        strHexString.append('0')
                    }
                    strHexString.append(hex)
                }
                // 得到返回結果
                strResult = strHexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

        }

        return strResult
    }

    /**
     * base64加密方法
     *
     * @param plainText
     * @return
     */
    fun getEncodedBase64(plainText: String): String? {
        var encoded: String? = null
        try {
            val bytes = plainText.toByteArray(charset("UTF-8"))
            encoded = Base64.encodeToString(bytes,0)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return encoded
    }

    private fun getEncodedBase64(bytes: ByteArray): String {
        return Base64.encodeToString(bytes,0)
    }

    /**
     * BASE64解密算法
     *
     * @param plainText
     * @return
     */
    fun getDecodedBase64(plainText: String): ByteArray? {
        var decoded: ByteArray? = null
        try {
            val bytes = plainText.toByteArray(charset("UTF-8"))
            decoded = Base64.decode(bytes,0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return decoded
    }

    /**
     * AES加密
     * @param content 明文
     * @param encryptKey 密码
     * @return String AES加密内容
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun aesEncryptToBytes(content: String, encryptKey: String): ByteArray {
        val kgen = KeyGenerator.getInstance("AES")
        kgen.init(128)
        val cipher = Cipher.getInstance(ALGORITHMSTR)//创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(encryptKey.toByteArray(), "AES")) //初始化

        return cipher.doFinal(content.toByteArray(charset("utf-8")))
    }

    /**
     * 对明文进行AES加密再进行BASE64加密
     * @param content AES加密内容
     * @param encryptKey 密码
     * @return String AES->BASE64加密内容
     * @throws Exception
     */
    @Throws(Exception::class)
    fun aesEncrypt(content: String, encryptKey: String): String {
        return getEncodedBase64(aesEncryptToBytes(content, encryptKey))
    }

    /**
     * AES解密
     * @param encryptBytes AES加密的内容
     * @param decryptKey 密码
     * @return String 明文
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun aesDecryptByBytes(encryptBytes: ByteArray?, decryptKey: String): String {
        val kgen = KeyGenerator.getInstance("AES")
        kgen.init(128)
        val cipher = Cipher.getInstance(ALGORITHMSTR)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(decryptKey.toByteArray(), "AES"))
        val decryptBytes = cipher.doFinal(encryptBytes)
        return String(decryptBytes)
    }

    /**
     * BASE64解密
     * @param encryptStr 经AES->BASE64加密的内容
     * @param decryptKey 密码
     * @return String AES解密后的BASE64加密的内容
     * @throws Exception
     */
    @Throws(Exception::class)
    fun aesDecrypt(encryptStr: String, decryptKey: String): String {
        return aesDecryptByBytes(getDecodedBase64(encryptStr), decryptKey)
    }
}