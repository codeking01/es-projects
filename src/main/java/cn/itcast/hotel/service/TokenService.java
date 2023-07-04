package cn.itcast.hotel.service;

public interface TokenService {
    /**
     * 创建token
     */
    String createToken(Long id);

    /**
     * 检验token 
     */
    boolean checkToken(Long id) throws Exception;

}
