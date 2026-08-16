package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.ImporterInfoMaintainService;
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
public class ImporterInfoMaintainServiceImplTest {

    private static final Long IMPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey IMPORT_TASK_SETTING_KEY = new LongIdKey(IMPORT_TASK_SETTING_ID);

    @Autowired
    private ImportTaskSettingMaintainService importTaskSettingMaintainService;
    @Autowired
    private ImporterInfoMaintainService importerInfoMaintainService;

    private ImportTaskSetting importTaskSetting;
    private final List<ImporterInfo> importerInfos = new ArrayList<>();

    @Before
    public void setUp() {
        importTaskSetting = new ImportTaskSetting(IMPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ImporterInfo ImporterInfo = new ImporterInfo(
                    new TaskSettingItemKey(IMPORT_TASK_SETTING_ID, "test." + i),
                    true, "type", "param", "remark"
            );
            importerInfos.add(ImporterInfo);
        }
    }

    @After
    public void tearDown() {
        importTaskSetting = null;
        importerInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (ImporterInfo importerInfo : importerInfos) {
                importerInfoMaintainService.insertOrUpdate(importerInfo);
                importerInfoMaintainService.update(importerInfo);
                ImporterInfo testImporterInfo = importerInfoMaintainService.get(importerInfo.getKey());
                assertEquals(BeanUtils.describe(importerInfo), BeanUtils.describe(testImporterInfo));
            }
        } finally {
            for (ImporterInfo ImporterInfo : importerInfos) {
                if (Objects.isNull(ImporterInfo.getKey())) {
                    continue;
                }
                importerInfoMaintainService.deleteIfExists(ImporterInfo.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (ImporterInfo importerInfo : importerInfos) {
                importerInfoMaintainService.insertOrUpdate(importerInfo);
            }

            assertEquals(
                    importerInfos.size(),
                    importerInfoMaintainService.lookupAsList(
                            ImporterInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    importerInfoMaintainService.lookupAsList(
                            ImporterInfoMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ImporterInfo importerInfo : importerInfos) {
                assertFalse(importerInfoMaintainService.exists(importerInfo.getKey()));
            }
        } finally {
            for (ImporterInfo ImporterInfo : importerInfos) {
                if (Objects.isNull(ImporterInfo.getKey())) {
                    continue;
                }
                importerInfoMaintainService.deleteIfExists(ImporterInfo.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }
}
