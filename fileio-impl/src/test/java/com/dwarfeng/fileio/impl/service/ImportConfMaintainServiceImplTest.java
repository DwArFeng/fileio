package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImportConfMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
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
public class ImportConfMaintainServiceImplTest {

    private static final Long IMPORT_TASK_SETTING_ID = 12450L;
    private static final LongIdKey IMPORT_TASK_SETTING_KEY = new LongIdKey(IMPORT_TASK_SETTING_ID);

    @Autowired
    private ImportTaskSettingMaintainService importTaskSettingMaintainService;
    @Autowired
    private ImportConfMaintainService importConfMaintainService;

    private ImportTaskSetting importTaskSetting;
    private final List<ImportConf> importConfs = new ArrayList<>();

    @Before
    public void setUp() {
        importTaskSetting = new ImportTaskSetting(IMPORT_TASK_SETTING_KEY, true, "name", "remark");
        for (int i = 0; i < 5; i++) {
            ImportConf ImportConf = new ImportConf(
                    new TaskSettingItemKey(IMPORT_TASK_SETTING_ID, "test." + i), "content", "remark"
            );
            importConfs.add(ImportConf);
        }
    }

    @After
    public void tearDown() {
        importTaskSetting = null;
        importConfs.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (ImportConf importConf : importConfs) {
                importConfMaintainService.insertOrUpdate(importConf);
                importConfMaintainService.update(importConf);
                ImportConf testImportConf = importConfMaintainService.get(importConf.getKey());
                assertEquals(BeanUtils.describe(importConf), BeanUtils.describe(testImportConf));
            }
        } finally {
            for (ImportConf ImportConf : importConfs) {
                if (Objects.isNull(ImportConf.getKey())) {
                    continue;
                }
                importConfMaintainService.deleteIfExists(ImportConf.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            for (ImportConf importConf : importConfs) {
                importConfMaintainService.insertOrUpdate(importConf);
            }

            assertEquals(
                    importConfs.size(),
                    importConfMaintainService.lookupAsList(
                            ImportConfMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);

            assertEquals(
                    0,
                    importConfMaintainService.lookupAsList(
                            ImportConfMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ImportConf importConf : importConfs) {
                assertFalse(importConfMaintainService.exists(importConf.getKey()));
            }
        } finally {
            for (ImportConf ImportConf : importConfs) {
                if (Objects.isNull(ImportConf.getKey())) {
                    continue;
                }
                importConfMaintainService.deleteIfExists(ImportConf.getKey());
            }
            importTaskSettingMaintainService.deleteIfExists(IMPORT_TASK_SETTING_KEY);
        }
    }
}
