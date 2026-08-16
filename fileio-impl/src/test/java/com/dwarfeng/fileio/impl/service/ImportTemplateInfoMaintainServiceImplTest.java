package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTemplateInfoMaintainService;
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
public class ImportTemplateInfoMaintainServiceImplTest {

    private static final Long IMPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey IMPORT_TASK_SETTING_KEY = new LongIdKey(IMPORT_TASK_SETTING_ID);

    @Autowired
    private ImportTaskSettingMaintainService importTaskSettingMaintainService;
    @Autowired
    private ImportTemplateInfoMaintainService importTemplateInfoMaintainService;

    private ImportTaskSetting importTaskSetting;
    private final List<ImportTemplateInfo> importTemplateInfos = new ArrayList<>();

    @Before
    public void setUp() {
        importTaskSetting = new ImportTaskSetting(IMPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ImportTemplateInfo ImportTemplateInfo = new ImportTemplateInfo(
                    new TaskSettingItemKey(IMPORT_TASK_SETTING_ID, "test." + i),
                    "originName", 12450, new Date(), "remark"
            );
            importTemplateInfos.add(ImportTemplateInfo);
        }
    }

    @After
    public void tearDown() {
        importTaskSetting = null;
        importTemplateInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (ImportTemplateInfo importTemplateInfo : importTemplateInfos) {
                importTemplateInfoMaintainService.insertOrUpdate(importTemplateInfo);
                importTemplateInfoMaintainService.update(importTemplateInfo);
                ImportTemplateInfo testImportTemplateInfo = importTemplateInfoMaintainService.get(
                        importTemplateInfo.getKey()
                );
                assertEquals(BeanUtils.describe(importTemplateInfo), BeanUtils.describe(testImportTemplateInfo));
            }
        } finally {
            for (ImportTemplateInfo ImportTemplateInfo : importTemplateInfos) {
                if (Objects.isNull(ImportTemplateInfo.getKey())) {
                    continue;
                }
                importTemplateInfoMaintainService.deleteIfExists(ImportTemplateInfo.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (ImportTemplateInfo importTemplateInfo : importTemplateInfos) {
                importTemplateInfoMaintainService.insertOrUpdate(importTemplateInfo);
            }

            assertEquals(
                    importTemplateInfos.size(),
                    importTemplateInfoMaintainService.lookupAsList(
                            ImportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    importTemplateInfoMaintainService.lookupAsList(
                            ImportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ImportTemplateInfo importTemplateInfo : importTemplateInfos) {
                assertFalse(importTemplateInfoMaintainService.exists(importTemplateInfo.getKey()));
            }
        } finally {
            for (ImportTemplateInfo ImportTemplateInfo : importTemplateInfos) {
                if (Objects.isNull(ImportTemplateInfo.getKey())) {
                    continue;
                }
                importTemplateInfoMaintainService.deleteIfExists(ImportTemplateInfo.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }
}
