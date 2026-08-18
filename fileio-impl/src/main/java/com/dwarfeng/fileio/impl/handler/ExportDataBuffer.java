package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.struct.ReadResult;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 导出数据缓冲。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ExportDataBuffer {

    private final Queue<DataBlock> bufferQueue = new LinkedList<>();
    private final Map<String, Boolean> endOfInputMap = new HashMap<>();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private boolean endOfOutput = false;

    public ExportDataBuffer(Set<String> identifierSpace) {
        identifierSpace.forEach(identifier -> endOfInputMap.put(identifier, false));
    }

    public boolean isEndOfOutput() {
        lock.lock();
        try {
            return endOfOutput;
        } finally {
            lock.unlock();
        }
    }

    public void put(DataBlock dataBlock) {
        lock.lock();
        try {
            if (endOfOutput) {
                return;
            }

            String identifier = dataBlock.getIdentifier();

            if (!endOfInputMap.containsKey(identifier)) {
                return;
            }

            if (endOfInputMap.get(identifier)) {
                return;
            }

            boolean hasNext = dataBlock.getReadResult().isHasNext();

            bufferQueue.offer(dataBlock);

            if (!hasNext) {
                endOfInputMap.put(identifier, true);
            }

            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public DataBlock take() {
        lock.lock();
        try {
            if (endOfOutput) {
                return null;
            }

            while (bufferQueue.isEmpty()) {
                condition.awaitUninterruptibly();
            }

            DataBlock data = bufferQueue.poll();

            boolean allEndOfInput = endOfInputMap.values().stream().allMatch(Boolean::booleanValue);
            if (allEndOfInput && bufferQueue.isEmpty()) {
                endOfOutput = true;
            }

            return data;
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public void markEndOfInput(String identifier) {
        lock.lock();
        try {
            if (endOfOutput) {
                return;
            }

            if (!endOfInputMap.containsKey(identifier)) {
                return;
            }

            endOfInputMap.put(identifier, true);

            boolean allEndOfInput = endOfInputMap.values().stream().allMatch(Boolean::booleanValue);
            if (allEndOfInput && bufferQueue.isEmpty()) {
                endOfOutput = true;
            }

            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void markEndOfOutput() {
        lock.lock();
        try {
            if (endOfOutput) {
                return;
            }

            bufferQueue.clear();

            endOfOutput = true;

            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "ExportDataBuffer{" +
                "bufferQueue=" + bufferQueue +
                ", endOfInputMap=" + endOfInputMap +
                ", endOfOutput=" + endOfOutput +
                '}';
    }

    /**
     * 数据块。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static final class DataBlock {

        private final String identifier;
        private final String topic;
        private final ReadResult readResult;

        public DataBlock(String identifier, String topic, ReadResult readResult) {
            this.identifier = identifier;
            this.topic = topic;
            this.readResult = readResult;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getTopic() {
            return topic;
        }

        public ReadResult getReadResult() {
            return readResult;
        }

        @Override
        public String toString() {
            return "DataBlock{" +
                    "identifier='" + identifier + '\'' +
                    ", readResult=" + readResult +
                    '}';
        }
    }
}
