package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExportConfMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
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
public class ExportConfMaintainServiceImplTest {

    private static final Long EXPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey EXPORT_TASK_SETTING_KEY = new LongIdKey(EXPORT_TASK_SETTING_ID);

    @Autowired
    private ExportTaskSettingMaintainService exportTaskSettingMaintainService;
    @Autowired
    private ExportConfMaintainService exportConfMaintainService;

    private ExportTaskSetting exportTaskSetting;
    private final List<ExportConf> exportConfs = new ArrayList<>();

    @Before
    public void setUp() {
        exportTaskSetting = new ExportTaskSetting(EXPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ExportConf ExportConf = new ExportConf(
                    new TaskSettingItemKey(EXPORT_TASK_SETTING_ID, "test." + i), "content", "remark"
            );
            exportConfs.add(ExportConf);
        }
    }

    @After
    public void tearDown() {
        exportTaskSetting = null;
        exportConfs.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ExportConf exportConf : exportConfs) {
                exportConfMaintainService.insertOrUpdate(exportConf);
                exportConfMaintainService.update(exportConf);
                ExportConf testExportConf = exportConfMaintainService.get(exportConf.getKey());
                assertEquals(BeanUtils.describe(exportConf), BeanUtils.describe(testExportConf));
            }
        } finally {
            for (ExportConf ExportConf : exportConfs) {
                if (Objects.isNull(ExportConf.getKey())) {
                    continue;
                }
                exportConfMaintainService.deleteIfExists(ExportConf.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            for (ExportConf exportConf : exportConfs) {
                exportConfMaintainService.insertOrUpdate(exportConf);
            }

            assertEquals(
                    exportConfs.size(),
                    exportConfMaintainService.lookupAsList(
                            ExportConfMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    exportConfMaintainService.lookupAsList(
                            ExportConfMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ExportConf exportConf : exportConfs) {
                assertFalse(exportConfMaintainService.exists(exportConf.getKey()));
            }
        } finally {
            for (ExportConf ExportConf : exportConfs) {
                if (Objects.isNull(ExportConf.getKey())) {
                    continue;
                }
                exportConfMaintainService.deleteIfExists(ExportConf.getKey());
            }
            exportTaskSettingMaintainService.deleteIfExists(EXPORT_TASK_SETTING_KEY);
        }
    }
}
