package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Date;

/**
 * 任务死亡日期未到异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskDeadDateNotReachedException extends HandlerException {

    private static final long serialVersionUID = -7414896937032304159L;

    private final Date shouldDeadDate;
    private final Date currenDate;

    public TaskDeadDateNotReachedException(Date shouldDeadDate, Date currenDate) {
        this.shouldDeadDate = shouldDeadDate;
        this.currenDate = currenDate;
    }

    public TaskDeadDateNotReachedException(Throwable cause, Date shouldDeadDate, Date currenDate) {
        super(cause);
        this.shouldDeadDate = shouldDeadDate;
        this.currenDate = currenDate;
    }

    @Override
    public String getMessage() {
        return "任务死亡日期未到, 期望的死亡日期为 " + shouldDeadDate + ", 实际的日期为 " + currenDate;
    }
}
