package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ImportMetadataMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ImportMetadataMaintainServiceImplTest {

    private static final Long IMPORT_TASK_ID = 12450L;
    private static final LongIdKey IMPORT_TASK_KEY = new LongIdKey(IMPORT_TASK_ID);

    @Autowired
    private ImportTaskMaintainService importTaskMaintainService;
    @Autowired
    private ImportMetadataMaintainService importMetadataMaintainService;

    private ImportTask importTask;
    private final List<ImportMetadata> importMetadatas = new ArrayList<>();

    @Before
    public void setUp() {
        importTask = new ImportTask(
                IMPORT_TASK_KEY, null, null, 12450, new Date(), new Date(), new Date(), 12450L, 12450, "message",
                new Date(), new Date()
        );
        for (int i = 0; i < 5; i++) {
            ImportMetadata ImportMetadata = new ImportMetadata(
                    new TaskItemKey(IMPORT_TASK_ID, "test." + i), "content", "remark"
            );
            importMetadatas.add(ImportMetadata);
        }
    }

    @After
    public void tearDown() {
        importTask = null;
        importMetadatas.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskMaintainService.insertOrUpdate(importTask);
            for (ImportMetadata importMetadata : importMetadatas) {
                importMetadataMaintainService.insertOrUpdate(importMetadata);
                importMetadataMaintainService.update(importMetadata);
                ImportMetadata testImportMetadata = importMetadataMaintainService.get(importMetadata.getKey());
                assertEquals(BeanUtils.describe(importMetadata), BeanUtils.describe(testImportMetadata));
            }
        } finally {
            for (ImportMetadata ImportMetadata : importMetadatas) {
                if (Objects.isNull(ImportMetadata.getKey())) {
                    continue;
                }
                importMetadataMaintainService.deleteIfExists(ImportMetadata.getKey());
            }
            importTaskMaintainService.deleteIfExists(IMPORT_TASK_KEY);
        }
    }

    @Test
    public void testForTaskCascade() throws Exception {
        try {
            importTaskMaintainService.insertOrUpdate(importTask);
            for (ImportMetadata importMetadata : importMetadatas) {
                importMetadataMaintainService.insertOrUpdate(importMetadata);
            }

            assertEquals(
                    importMetadatas.size(),
                    importMetadataMaintainService.lookupAsList(
                            ImportMetadataMaintainService.CHILD_FOR_TASK,
                            new Object[]{IMPORT_TASK_KEY}
                    ).size()
            );

            importTaskMaintainService.deleteIfExists(IMPORT_TASK_KEY);

            assertEquals(
                    0,
                    importMetadataMaintainService.lookupAsList(
                            ImportMetadataMaintainService.CHILD_FOR_TASK,
                            new Object[]{IMPORT_TASK_KEY}
                    ).size()
            );

            for (ImportMetadata importMetadata : importMetadatas) {
                assertFalse(importMetadataMaintainService.exists(importMetadata.getKey()));
            }
        } finally {
            for (ImportMetadata ImportMetadata : importMetadatas) {
                if (Objects.isNull(ImportMetadata.getKey())) {
                    continue;
                }
                importMetadataMaintainService.deleteIfExists(ImportMetadata.getKey());
            }
            importTaskMaintainService.deleteIfExists(IMPORT_TASK_KEY);
        }
    }
}
