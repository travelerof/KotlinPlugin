package com.hyg.permission.execute

import java.util.concurrent.LinkedBlockingQueue

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
object PermissionExecutorManager {

    private val executors = LinkedBlockingQueue<IExecutor>()

    private val executeCallback = object : IExecuteCallback{
        override fun onCompleted() {
            isExecuting = false
            execute()
        }
    }
    /**
     * 是否正在执行请求
     */
    private var isExecuting = false

    fun push(executor: IExecutor): PermissionExecutorManager{
        if (!executors.contains(executor)) {
            executor.setExecuteCallback(executeCallback)
            executors.offer(executor)
        }
        return this
    }

    fun execute(){
        if (isExecuting || executors.isEmpty()) {
            return
        }
        executors.poll()?.let {
            it.execute()
            isExecuting = true
        }
    }
}