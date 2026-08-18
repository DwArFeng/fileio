package com.dwarfeng.fileio.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Date;

/**
 * 任务过期日期未到异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskExpireDateNotReachedException extends HandlerException {

    private static final long serialVersionUID = -6293993391796518157L;

    private final Date shouldExpireDate;
    private final Date currenDate;

    public TaskExpireDateNotReachedException(Date shouldExpireDate, Date currenDate) {
        this.shouldExpireDate = shouldExpireDate;
        this.currenDate = currenDate;
    }

    public TaskExpireDateNotReachedException(Throwable cause, Date shouldExpireDate, Date currenDate) {
        super(cause);
        this.shouldExpireDate = shouldExpireDate;
        this.currenDate = currenDate;
    }

    @Override
    public String getMessage() {
        return "任务过期日期未到, 期望的过期日期为 " + shouldExpireDate + ", 实际的日期为 " + currenDate;
    }
}
