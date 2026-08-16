package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTemplateInfoMaintainService;
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
public class ExportTemplateInfoMaintainServiceImplTest {

    private static final Long EXPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey EXPORT_TASK_SETTING_KEY = new LongIdKey(EXPORT_TASK_SETTING_ID);

    @Autowired
    private ExportTaskSettingMaintainService exportTaskSettingMaintainService;
    @Autowired
    private ExportTemplateInfoMaintainService exportTemplateInfoMaintainService;

    private ExportTaskSetting exportTaskSetting;
    private final List<ExportTemplateInfo> exportTemplateInfos = new ArrayList<>();

    @Before
    public void setUp() {
        exportTaskSetting = new ExportTaskSetting(EXPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ExportTemplateInfo ExportTemplateInfo = new ExportTemplateInfo(
                    new TaskSettingItemKey(EXPORT_TASK_SETTING_ID, "test." + i),
                    "originName", 12450, new Date(), "remark"
            );
            exportTemplateInfos.add(ExportTemplateInfo);
        }
    }

    @After
    public void tearDown() {
        exportTaskSetting = null;
        exportTemplateInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ExportTemplateInfo exportTemplateInfo : exportTemplateInfos) {
                exportTemplateInfoMaintainService.insertOrUpdate(exportTemplateInfo);
                exportTemplateInfoMaintainService.update(exportTemplateInfo);
                ExportTemplateInfo testExportTemplateInfo = exportTemplateInfoMaintainService.get(
                        exportTemplateInfo.getKey()
                );
                assertEquals(BeanUtils.describe(exportTemplateInfo), BeanUtils.describe(testExportTemplateInfo));
            }
        } finally {
            for (ExportTemplateInfo ExportTemplateInfo : exportTemplateInfos) {
                if (Objects.isNull(ExportTemplateInfo.getKey())) {
                    continue;
                }
                exportTemplateInfoMaintainService.deleteIfExists(ExportTemplateInfo.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ExportTemplateInfo exportTemplateInfo : exportTemplateInfos) {
                exportTemplateInfoMaintainService.insertOrUpdate(exportTemplateInfo);
            }

            assertEquals(
                    exportTemplateInfos.size(),
                    exportTemplateInfoMaintainService.lookupAsList(
                            ExportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    exportTemplateInfoMaintainService.lookupAsList(
                            ExportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ExportTemplateInfo exportTemplateInfo : exportTemplateInfos) {
                assertFalse(exportTemplateInfoMaintainService.exists(exportTemplateInfo.getKey()));
            }
        } finally {
            for (ExportTemplateInfo ExportTemplateInfo : exportTemplateInfos) {
                if (Objects.isNull(ExportTemplateInfo.getKey())) {
                    continue;
                }
                exportTemplateInfoMaintainService.deleteIfExists(ExportTemplateInfo.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }
}
