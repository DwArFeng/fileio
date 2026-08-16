package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.ReaderInfoMaintainService;
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
public class ReaderInfoMaintainServiceImplTest {

    private static final Long EXPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey EXPORT_TASK_SETTING_KEY = new LongIdKey(EXPORT_TASK_SETTING_ID);

    @Autowired
    private ExportTaskSettingMaintainService exportTaskSettingMaintainService;
    @Autowired
    private ReaderInfoMaintainService readerInfoMaintainService;

    private ExportTaskSetting exportTaskSetting;
    private final List<ReaderInfo> readerInfos = new ArrayList<>();

    @Before
    public void setUp() {
        exportTaskSetting = new ExportTaskSetting(EXPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ReaderInfo ReaderInfo = new ReaderInfo(
                    new TaskSettingItemKey(EXPORT_TASK_SETTING_ID, "test." + i),
                    true, "type", "param", "remark"
            );
            readerInfos.add(ReaderInfo);
        }
    }

    @After
    public void tearDown() {
        exportTaskSetting = null;
        readerInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ReaderInfo readerInfo : readerInfos) {
                readerInfoMaintainService.insertOrUpdate(readerInfo);
                readerInfoMaintainService.update(readerInfo);
                ReaderInfo testReaderInfo = readerInfoMaintainService.get(readerInfo.getKey());
                assertEquals(BeanUtils.describe(readerInfo), BeanUtils.describe(testReaderInfo));
            }
        } finally {
            for (ReaderInfo ReaderInfo : readerInfos) {
                readerInfoMaintainService.deleteIfExists(ReaderInfo.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ReaderInfo readerInfo : readerInfos) {
                readerInfoMaintainService.insertOrUpdate(readerInfo);
            }

            assertEquals(
                    readerInfos.size(),
                    readerInfoMaintainService.lookupAsList(
                            ReaderInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    readerInfoMaintainService.lookupAsList(
                            ReaderInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ReaderInfo readerInfo : readerInfos) {
                assertFalse(readerInfoMaintainService.exists(readerInfo.getKey()));
            }
        } finally {
            for (ReaderInfo ReaderInfo : readerInfos) {
                readerInfoMaintainService.deleteIfExists(ReaderInfo.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }
}
