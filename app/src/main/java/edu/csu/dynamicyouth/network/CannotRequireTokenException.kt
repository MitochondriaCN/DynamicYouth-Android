package edu.csu.dynamicyouth.network

/**
 * 即便要求登录也无法获得有效token时，应当抛出此异常。
 */
class CannotRequireTokenException: Exception() {
}