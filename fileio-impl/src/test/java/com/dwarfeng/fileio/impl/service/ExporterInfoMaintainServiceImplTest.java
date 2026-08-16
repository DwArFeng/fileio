package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ExporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.ExporterInfoMaintainService;
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
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ExporterInfoMaintainServiceImplTest {

    private static final Long EXPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey EXPORT_TASK_SETTING_KEY = new LongIdKey(EXPORT_TASK_SETTING_ID);

    @Autowired
    private ExportTaskSettingMaintainService exportTaskSettingMaintainService;
    @Autowired
    private ExporterInfoMaintainService exporterInfoMaintainService;

    private ExportTaskSetting exportTaskSetting;
    private final List<ExporterInfo> exporterInfos = new ArrayList<>();

    @Before
    public void setUp() {
        exportTaskSetting = new ExportTaskSetting(EXPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ExporterInfo ExporterInfo = new ExporterInfo(
                    new TaskSettingItemKey(EXPORT_TASK_SETTING_ID, "test." + i),
                    true, "type", "param", "remark"
            );
            exporterInfos.add(ExporterInfo);
        }
    }

    @After
    public void tearDown() {
        exportTaskSetting = null;
        exporterInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ExporterInfo exporterInfo : exporterInfos) {
                exporterInfoMaintainService.insertOrUpdate(exporterInfo);
                exporterInfoMaintainService.update(exporterInfo);
                ExporterInfo testExporterInfo = exporterInfoMaintainService.get(exporterInfo.getKey());
                assertEquals(BeanUtils.describe(exporterInfo), BeanUtils.describe(testExporterInfo));
            }
        } finally {
            for (ExporterInfo ExporterInfo : exporterInfos) {
                if (Objects.isNull(ExporterInfo.getKey())) {
                    continue;
                }
                exporterInfoMaintainService.deleteIfExists(ExporterInfo.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ExporterInfo exporterInfo : exporterInfos) {
                exporterInfoMaintainService.insertOrUpdate(exporterInfo);
            }

            assertEquals(
                    exporterInfos.size(),
                    exporterInfoMaintainService.lookupAsList(
                            ExporterInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    exporterInfoMaintainService.lookupAsList(
                            ExporterInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ExporterInfo exporterInfo : exporterInfos) {
                assertFalse(exporterInfoMaintainService.exists(exporterInfo.getKey()));
            }
        } finally {
            for (ExporterInfo ExporterInfo : exporterInfos) {
                if (Objects.isNull(ExporterInfo.getKey())) {
                    continue;
                }
                exporterInfoMaintainService.deleteIfExists(ExporterInfo.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }
}
