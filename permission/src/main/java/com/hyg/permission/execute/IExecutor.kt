package com.hyg.permission.execute

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
interface IExecutor {
    /**
     * 设置任务执行完成监听
     *
     * @param callback IExecuteCallback
     */
    fun setExecuteCallback(callback: IExecuteCallback)

    /**
     * 执行
     */
    fun execute()
}