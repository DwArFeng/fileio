package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.struct.ImportResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * 导入器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Importer {

    /**
     * 生成一个新的导入器执行器。
     *
     * @return 新的导入器执行器。
     * @throws HandlerException 处理器异常。
     */
    Executor newExecutor() throws HandlerException;

    /**
     * 导入器执行器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Executor {

        /**
         * 初始化导入器执行器。
         *
         * @param context 指定的导入器上下文。
         */
        void init(Context context);

        /**
         * 获取导入器执行器的主题空间。
         *
         * <p>
         * 该方法会在 {@link #beforeExecute()} 调度之前被调用，用于确定导入器执行器的主题空间。
         *
         * <p>
         * 主题空间是一个字符串集合，集合中的主题互相不得重复，主题允许为 <code>null</code>。
         *
         * @return 主题空间。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        Set<String> topicSpace() throws Exception;

        /**
         * 执行前调度。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void beforeExecute() throws Exception;

        /**
         * 执行调度。
         *
         * <p>
         * 该方法会根据 {@link #topicSpace()} 返回的主题空间，依次对每个主题进行调用。
         *
         * <p>
         * 该方法会被多次调用，直到返回的 {@link ImportResult#isHasNext()} 返回 false。
         *
         * @return 导入结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        ImportResult execute(String topic) throws Exception;

        /**
         * 执行后调度。
         *
         * <p>
         * 该调度在 {@link #execute(String)} 调度之抛出异常后同样会被调用。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void afterExecute() throws Exception;
    }

    /**
     * 导入器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 获取指定标识符对应的配置。
         *
         * @param identifier 指定的标识符。
         * @return 指定的标识符对应的配置。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        ImportConf getConf(String identifier) throws Exception;

        /**
         * 获取模板。
         *
         * @param info 模板获取信息。
         * @return 获取的模板。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        Template retrieveTemplate(TemplateRetrieveInfo info) throws Exception;

        /**
         * 获取模板流。
         *
         * <p>
         * 如果返回的 <code>TemplateStream</code> 不为 <code>null</code>，则调用者有义务关闭
         * <code>TemplateStream</code> 中的输入流，即其中的 <code>TemplateStream.content</code>。
         *
         * @param info 模板流获取信息。
         * @return 获取的模板流。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        TemplateStream retrieveTemplateStream(TemplateStreamRetrieveInfo info) throws Exception;

        /**
         * 打开指定标识符对应的文件输出流。
         *
         * @param identifier 指定的标识符。
         * @return 指定的标识符对应的文件输出流。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        ImportMetadata getMetadata(String identifier) throws Exception;

        /**
         * 获取文件。
         *
         * @param info 文件获取信息。
         * @return 获取的文件。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        File retrieveFile(FileRetrieveInfo info) throws Exception;

        /**
         * 获取文件流。
         *
         * <p>
         * 如果返回的 <code>File</code> 不为 <code>null</code>，则调用者有义务关闭
         * <code>File</code> 中的输入流，即其中的 <code>File.content</code>。
         *
         * @param info 文件流获取信息。
         * @return 获取的文件流。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        FileStream retrieveFileStream(FileStreamRetrieveInfo info) throws Exception;

        /**
         * 存储文件。
         *
         * @param info 文件存储信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void storeFile(FileStoreInfo info) throws Exception;

        /**
         * 存储文件流。
         *
         * <p>
         * 调用者有义务关闭 <code>FileStreamStoreInfo</code> 中的输入流，
         * 即其中的 <code>FileStreamStoreInfo.content</code>。
         *
         * @param info 文件流存储信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void storeFileStream(FileStreamStoreInfo info) throws Exception;

        /**
         * 移除指定标识符对应的文件。
         *
         * @param identifier 指定的标识符。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void removeFile(String identifier) throws Exception;

        /**
         * 创建指定的文件名对应的临时文件。
         *
         * @param name 指定的文件名。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void createTemporary(String name) throws Exception;

        /**
         * 打开指定文件名对应的临时文件输入流。
         *
         * @param name 指定的文件名。
         * @return 指定的文件名对应的临时文件输入流。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        InputStream openTemporaryInputStream(String name) throws Exception;

        /**
         * 打开指定文件名对应的临时文件输出流。
         *
         * @param name 指定的文件名。
         * @return 指定的文件名对应的临时文件输出流。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        OutputStream openTemporaryOutputStream(String name) throws Exception;

        /**
         * 将指定标识符对应的模板文件中的内容复制到指定文件名对应的临时文件中。
         *
         * @param templateIdentifier 指定的标识符。
         * @param temporaryName      指定的文件名。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void copyTemplateToTemporary(String templateIdentifier, String temporaryName) throws Exception;

        /**
         * 将指定文件名对应的临时文件中的内容复制到指定标识符对应的文件中。
         *
         * @param temporaryName  指定的文件名。
         * @param fileIdentifier 指定的标识符。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void copyFileToTemporary(String fileIdentifier, String temporaryName) throws Exception;

        /**
         * 将指定文件名对应的临时文件中的内容复制到指定标识符对应的文件中。
         *
         * <p>
         * 该方法拷贝文件时，会将 temporaryName 作为文件的原始名称。<br>
         * 如果想在拷贝文件时自定义文件的原始名称或其它属性，
         * 请使用 {@link #copyTemporaryToFile(TemporaryToFileCopyInfo)} 方法。
         *
         * @param temporaryName  指定的文件名。
         * @param fileIdentifier 指定的标识符。
         * @throws Exception 方法执行过程中发生的任何异常。
         * @see #copyTemporaryToFile(TemporaryToFileCopyInfo)
         */
        void copyTemporaryToFile(String temporaryName, String fileIdentifier) throws Exception;

        /**
         * 将指定文件名对应的临时文件中的内容复制到指定文件名对应的临时文件中。
         *
         * @param fromTemporaryName 指定的文件名。
         * @param toTemporaryName   指定的文件名。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void copyTemporaryToTemporary(String fromTemporaryName, String toTemporaryName) throws Exception;

        /**
         * 将指定文件名对应的临时文件中的内容复制到指定标识符对应的文件中。
         *
         * @param info 指定的拷贝信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         * @since 1.0.0
         */
        void copyTemporaryToFile(TemporaryToFileCopyInfo info) throws Exception;
    }
}
