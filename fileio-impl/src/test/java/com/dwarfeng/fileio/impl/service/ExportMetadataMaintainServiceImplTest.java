package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ExportMetadataMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
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
public class ExportMetadataMaintainServiceImplTest {

    private static final Long EXPORT_TASK_ID = 12450L;
    private static final LongIdKey EXPORT_TASK_KEY = new LongIdKey(EXPORT_TASK_ID);

    @Autowired
    private ExportTaskMaintainService exportTaskMaintainService;
    @Autowired
    private ExportMetadataMaintainService exportMetadataMaintainService;

    private ExportTask exportTask;
    private final List<ExportMetadata> exportMetadatas = new ArrayList<>();

    @Before
    public void setUp() {
        exportTask = new ExportTask(
                EXPORT_TASK_KEY, null, null, 12450, new Date(), new Date(), new Date(), 12450L, 12450, "message",
                new Date(), new Date()
        );
        for (int i = 0; i < 5; i++) {
            ExportMetadata ExportMetadata = new ExportMetadata(
                    new TaskItemKey(EXPORT_TASK_ID, "test." + i), "content", "remark"
            );
            exportMetadatas.add(ExportMetadata);
        }
    }

    @After
    public void tearDown() {
        exportTask = null;
        exportMetadatas.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            exportTaskMaintainService.insertOrUpdate(exportTask);
            for (ExportMetadata exportMetadata : exportMetadatas) {
                exportMetadataMaintainService.insertOrUpdate(exportMetadata);
                exportMetadataMaintainService.update(exportMetadata);
                ExportMetadata testExportMetadata = exportMetadataMaintainService.get(exportMetadata.getKey());
                assertEquals(BeanUtils.describe(exportMetadata), BeanUtils.describe(testExportMetadata));
            }
        } finally {
            for (ExportMetadata ExportMetadata : exportMetadatas) {
                if (Objects.isNull(ExportMetadata.getKey())) {
                    continue;
                }
                exportMetadataMaintainService.deleteIfExists(ExportMetadata.getKey());
            }
            exportTaskMaintainService.deleteIfExists(EXPORT_TASK_KEY);
        }
    }

    @Test
    public void testForTaskCascade() throws Exception {
        try {
            exportTaskMaintainService.insertOrUpdate(exportTask);
            for (ExportMetadata exportMetadata : exportMetadatas) {
                exportMetadataMaintainService.insertOrUpdate(exportMetadata);
            }

            assertEquals(
                    exportMetadatas.size(),
                    exportMetadataMaintainService.lookupAsList(
                            ExportMetadataMaintainService.CHILD_FOR_TASK,
                            new Object[]{EXPORT_TASK_KEY}
                    ).size()
            );

            exportTaskMaintainService.deleteIfExists(EXPORT_TASK_KEY);

            assertEquals(
                    0,
                    exportMetadataMaintainService.lookupAsList(
                            ExportMetadataMaintainService.CHILD_FOR_TASK,
                            new Object[]{EXPORT_TASK_KEY}
                    ).size()
            );

            for (ExportMetadata exportMetadata : exportMetadatas) {
                assertFalse(exportMetadataMaintainService.exists(exportMetadata.getKey()));
            }
        } finally {
            for (ExportMetadata ExportMetadata : exportMetadatas) {
                if (Objects.isNull(ExportMetadata.getKey())) {
                    continue;
                }
                exportMetadataMaintainService.deleteIfExists(ExportMetadata.getKey());
            }
            exportTaskMaintainService.deleteIfExists(EXPORT_TASK_KEY);
        }
    }
}
