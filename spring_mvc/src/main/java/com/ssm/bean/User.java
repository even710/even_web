package com.ssm.bean;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.User_Id
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.UserName
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Password
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.QQ
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    private String qq;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Salt
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    private String salt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.User_Id
     *
     * @return the value of user.User_Id
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.User_Id
     *
     * @param userId the value for user.User_Id
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.UserName
     *
     * @return the value of user.UserName
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.UserName
     *
     * @param username the value for user.UserName
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Password
     *
     * @return the value of user.Password
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Password
     *
     * @param password the value for user.Password
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.QQ
     *
     * @return the value of user.QQ
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public String getQq() {
        return qq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.QQ
     *
     * @param qq the value for user.QQ
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Salt
     *
     * @return the value of user.Salt
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Salt
     *
     * @param salt the value for user.Salt
     *
     * @mbggenerated Fri May 10 19:49:32 CST 2019
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
}