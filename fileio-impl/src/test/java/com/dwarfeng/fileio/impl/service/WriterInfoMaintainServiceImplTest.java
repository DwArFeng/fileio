package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.WriterInfoMaintainService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class WriterInfoMaintainServiceImplTest {

    private static final Long IMPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey IMPORT_TASK_SETTING_KEY = new LongIdKey(IMPORT_TASK_SETTING_ID);

    @Autowired
    private ImportTaskSettingMaintainService importTaskSettingMaintainService;
    @Autowired
    private WriterInfoMaintainService writerInfoMaintainService;

    private ImportTaskSetting importTaskSetting;
    private final List<WriterInfo> writerInfos = new ArrayList<>();

    @Before
    public void setUp() {
        importTaskSetting = new ImportTaskSetting(IMPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            WriterInfo WriterInfo = new WriterInfo(
                    new TaskSettingItemKey(IMPORT_TASK_SETTING_ID, "test." + i),
                    true, "type", "param", "remark"
            );
            writerInfos.add(WriterInfo);
        }
    }

    @After
    public void tearDown() {
        importTaskSetting = null;
        writerInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (WriterInfo writerInfo : writerInfos) {
                writerInfoMaintainService.insertOrUpdate(writerInfo);
                writerInfoMaintainService.update(writerInfo);
                WriterInfo testWriterInfo = writerInfoMaintainService.get(writerInfo.getKey());
                assertEquals(BeanUtils.describe(writerInfo), BeanUtils.describe(testWriterInfo));
            }
        } finally {
            for (WriterInfo WriterInfo : writerInfos) {
                writerInfoMaintainService.deleteIfExists(WriterInfo.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (WriterInfo writerInfo : writerInfos) {
                writerInfoMaintainService.insertOrUpdate(writerInfo);
            }

            assertEquals(
                    writerInfos.size(),
                    writerInfoMaintainService.lookupAsList(
                            WriterInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    writerInfoMaintainService.lookupAsList(
                            WriterInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (WriterInfo writerInfo : writerInfos) {
                assertFalse(writerInfoMaintainService.exists(writerInfo.getKey()));
            }
        } finally {
            for (WriterInfo WriterInfo : writerInfos) {
                writerInfoMaintainService.deleteIfExists(WriterInfo.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }
}
