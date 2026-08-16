package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ImportFileInfoMaintainService;
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
public class ImportFileInfoMaintainServiceImplTest {

    private static final Long IMPORT_TASK_ID = 12450L;
    private static final LongIdKey IMPORT_TASK_KEY = new LongIdKey(IMPORT_TASK_ID);

    @Autowired
    private ImportTaskMaintainService importTaskMaintainService;
    @Autowired
    private ImportFileInfoMaintainService importFileInfoMaintainService;

    private ImportTask importTask;
    private final List<ImportFileInfo> importFileInfos = new ArrayList<>();

    @Before
    public void setUp() {
        importTask = new ImportTask(
                IMPORT_TASK_KEY, null, null, 12450, new Date(), new Date(), new Date(), 12450L, 12450, "message",
                new Date(), new Date()
        );
        for (int i = 0; i < 5; i++) {
            ImportFileInfo ImportFileInfo = new ImportFileInfo(
                    new TaskItemKey(IMPORT_TASK_ID, "test." + i),
                    "originName", 12450, new Date(), "remark"
            );
            importFileInfos.add(ImportFileInfo);
        }
    }

    @After
    public void tearDown() {
        importTask = null;
        importFileInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskMaintainService.insertOrUpdate(importTask);
            for (ImportFileInfo importFileInfo : importFileInfos) {
                importFileInfoMaintainService.insertOrUpdate(importFileInfo);
                importFileInfoMaintainService.update(importFileInfo);
                ImportFileInfo testImportFileInfo = importFileInfoMaintainService.get(
                        importFileInfo.getKey()
                );
                assertEquals(BeanUtils.describe(importFileInfo), BeanUtils.describe(testImportFileInfo));
            }
        } finally {
            for (ImportFileInfo ImportFileInfo : importFileInfos) {
                if (Objects.isNull(ImportFileInfo.getKey())) {
                    continue;
                }
                importFileInfoMaintainService.deleteIfExists(ImportFileInfo.getKey());
            }
            importTaskMaintainService.deleteIfExists(IMPORT_TASK_KEY);
        }
    }

    @Test
    public void testForTaskCascade() throws Exception {
        try {
            importTaskMaintainService.insertOrUpdate(importTask);
            for (ImportFileInfo importFileInfo : importFileInfos) {
                importFileInfoMaintainService.insertOrUpdate(importFileInfo);
            }

            assertEquals(
                    importFileInfos.size(),
                    importFileInfoMaintainService.lookupAsList(
                            ImportFileInfoMaintainService.CHILD_FOR_TASK,
                            new Object[]{IMPORT_TASK_KEY}
                    ).size()
            );

            importTaskMaintainService.deleteIfExists(IMPORT_TASK_KEY);

            assertEquals(
                    0,
                    importFileInfoMaintainService.lookupAsList(
                            ImportFileInfoMaintainService.CHILD_FOR_TASK,
                            new Object[]{IMPORT_TASK_KEY}
                    ).size()
            );

            for (ImportFileInfo importFileInfo : importFileInfos) {
                assertFalse(importFileInfoMaintainService.exists(importFileInfo.getKey()));
            }
        } finally {
            for (ImportFileInfo ImportFileInfo : importFileInfos) {
                if (Objects.isNull(ImportFileInfo.getKey())) {
                    continue;
                }
                importFileInfoMaintainService.deleteIfExists(ImportFileInfo.getKey());
            }
            importTaskMaintainService.deleteIfExists(IMPORT_TASK_KEY);
        }
    }
}
