package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.exception.TemporaryFileException;
import com.dwarfeng.fileio.stack.exception.TemporaryFileNotExistsException;
import com.dwarfeng.fileio.stack.handler.TemporaryFileHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.tmpstg.stack.bean.dto.TemporaryStorageInfo;
import com.dwarfeng.tmpstg.stack.handler.TemporaryStorageHandler;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class TemporaryFileHandlerImpl implements TemporaryFileHandler {

    private final TemporaryStorageHandler temporaryStorageHandler;

    private final Map<TemporaryFileKey, String> identifierMap = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public TemporaryFileHandlerImpl(TemporaryStorageHandler temporaryStorageHandler) {
        this.temporaryStorageHandler = temporaryStorageHandler;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public long length(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException {
        try {
            // 构造临时文件键。
            TemporaryFileKey key = new TemporaryFileKey(type, taskKey, name);

            // 如果临时文件不存在，则抛出异常。
            lock.readLock().lock();
            try {
                if (!identifierMap.containsKey(key)) {
                    throw new TemporaryFileNotExistsException(type, taskKey, name);
                }
            } finally {
                lock.readLock().unlock();
            }

            // 获取临时文件标识符。
            String identifier;
            lock.readLock().lock();
            try {
                identifier = identifierMap.get(key);
            } finally {
                lock.readLock().unlock();
            }

            // 查看临时文件的。
            TemporaryStorageInfo storageInfo = temporaryStorageHandler.inspect(identifier);

            // 返回临时文件的长度。
            return storageInfo.getContentLength();
        } catch (TemporaryFileException e) {
            throw e;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void create(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException {
        try {
            // 构造临时文件键。
            TemporaryFileKey key = new TemporaryFileKey(type, taskKey, name);

            // 如果临时文件已经存在，则将旧文件删除。
            lock.readLock().lock();
            try {
                if (identifierMap.containsKey(key)) {
                    String oldIdentifier = identifierMap.get(key);
                    temporaryStorageHandler.disposeAndRemove(oldIdentifier);
                }
            } finally {
                lock.readLock().unlock();
            }

            // 调用 temporaryStorageHandler 创建临时文件，并获取其标识符。
            String identifier = temporaryStorageHandler.create();

            // 将临时文件标识符与临时文件键关联。
            lock.writeLock().lock();
            try {
                identifierMap.put(key, identifier);
            } finally {
                lock.writeLock().unlock();
            }
        } catch (TemporaryFileException e) {
            throw e;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public InputStream openInputStream(
            TemporaryFileType type, LongIdKey taskKey, String name
    ) throws HandlerException {
        try {
            // 构造临时文件键。
            TemporaryFileKey key = new TemporaryFileKey(type, taskKey, name);

            // 如果临时文件不存在，则抛出异常。
            lock.readLock().lock();
            try {
                if (!identifierMap.containsKey(key)) {
                    throw new TemporaryFileNotExistsException(type, taskKey, name);
                }
            } finally {
                lock.readLock().unlock();
            }

            // 获取临时文件标识符。
            String identifier;
            lock.readLock().lock();
            try {
                identifier = identifierMap.get(key);
            } finally {
                lock.readLock().unlock();
            }

            // 返回临时文件的输入流。
            return temporaryStorageHandler.openInputStream(identifier);
        } catch (TemporaryFileException e) {
            throw e;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public OutputStream openOutputStream(
            TemporaryFileType type, LongIdKey taskKey, String name
    ) throws HandlerException {
        try {
            // 构造临时文件键。
            TemporaryFileKey key = new TemporaryFileKey(type, taskKey, name);

            // 如果临时文件不存在，则抛出异常。
            lock.readLock().lock();
            try {
                if (!identifierMap.containsKey(key)) {
                    throw new TemporaryFileNotExistsException(type, taskKey, name);
                }
            } finally {
                lock.readLock().unlock();
            }

            // 获取临时文件标识符。
            String identifier;
            lock.readLock().lock();
            try {
                identifier = identifierMap.get(key);
            } finally {
                lock.readLock().unlock();
            }

            // 返回临时文件的输出流。
            return temporaryStorageHandler.openOutputStream(identifier);
        } catch (TemporaryFileException e) {
            throw e;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void delete(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException {
        try {
            // 构造临时文件键。
            TemporaryFileKey key = new TemporaryFileKey(type, taskKey, name);

            // 如果临时文件不存在，则抛出异常。
            lock.readLock().lock();
            try {
                if (!identifierMap.containsKey(key)) {
                    throw new TemporaryFileNotExistsException(type, taskKey, name);
                }
            } finally {
                lock.readLock().unlock();
            }

            // 获取临时文件标识符。
            String identifier;
            lock.readLock().lock();
            try {
                identifier = identifierMap.get(key);
            } finally {
                lock.readLock().unlock();
            }

            // 调用 temporaryStorageHandler 删除临时文件。
            temporaryStorageHandler.disposeAndRemove(identifier);

            // 移除临时文件键与临时文件标识符的关联。
            lock.writeLock().lock();
            try {
                identifierMap.remove(key);
            } finally {
                lock.writeLock().unlock();
            }
        } catch (TemporaryFileException e) {
            throw e;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private static final class TemporaryFileKey {

        private final TemporaryFileType type;
        private final LongIdKey taskKey;
        private final String name;

        public TemporaryFileKey(TemporaryFileType type, LongIdKey taskKey, String name) {
            this.type = type;
            this.taskKey = taskKey;
            this.name = name;
        }

        public TemporaryFileType getType() {
            return type;
        }

        public LongIdKey getTaskKey() {
            return taskKey;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TemporaryFileKey that = (TemporaryFileKey) o;
            return type == that.type && Objects.equals(taskKey, that.taskKey) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(type);
            result = 31 * result + Objects.hashCode(taskKey);
            result = 31 * result + Objects.hashCode(name);
            return result;
        }

        @Override
        public String toString() {
            return "TemporaryFileKey{" +
                    "type=" + type +
                    ", taskKey=" + taskKey +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
