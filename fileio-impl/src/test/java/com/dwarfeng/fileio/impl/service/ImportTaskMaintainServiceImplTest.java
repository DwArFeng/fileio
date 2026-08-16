package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.User;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.UserMaintainService;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ImportTaskMaintainServiceImplTest {

    private static final LongIdKey IMPORT_TASK_SETTING_KEY = new LongIdKey(12450L);
    private static final StringIdKey USER_KEY = new StringIdKey("test_user");

    @Autowired
    private ImportTaskSettingMaintainService importTaskSettingMaintainService;
    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private ImportTaskMaintainService importTaskMaintainService;

    private ImportTaskSetting importTaskSetting;
    private User user;
    private final List<ImportTask> importTasks = new ArrayList<>();

    @Before
    public void setUp() {
        importTaskSetting = new ImportTaskSetting(IMPORT_TASK_SETTING_KEY, true, "name", "remark");
        user = new User(USER_KEY, "remark");
        for (int i = 0; i < 5; i++) {
            ImportTask ImportTask = new ImportTask(
                    null, IMPORT_TASK_SETTING_KEY, USER_KEY, 12450, new Date(), new Date(), new Date(), 12450L, 12450,
                    "message", new Date(), new Date()
            );
            importTasks.add(ImportTask);
        }
    }

    @After
    public void tearDown() {
        importTaskSetting = null;
        importTasks.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            userMaintainService.insertOrUpdate(user);
            for (ImportTask importTask : importTasks) {
                importTask.setKey(importTaskMaintainService.insert(importTask));
                importTaskMaintainService.update(importTask);
                ImportTask testImportTask = importTaskMaintainService.get(importTask.getKey());
                assertEquals(BeanUtils.describe(importTask), BeanUtils.describe(testImportTask));
            }
        } finally {
            for (ImportTask ImportTask : importTasks) {
                if (Objects.isNull(ImportTask.getKey())) {
                    continue;
                }
                importTaskMaintainService.deleteIfExists(ImportTask.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(importTaskSetting.getKey())) {
                importTaskSettingMaintainService.deleteIfExists(importTaskSetting.getKey());
            }
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            userMaintainService.insertOrUpdate(user);
            for (ImportTask importTask : importTasks) {
                importTask.setKey(importTaskMaintainService.insert(importTask));
            }

            assertEquals(
                    importTasks.size(),
                    importTaskMaintainService.lookupAsList(
                            ImportTaskMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            importTaskSettingMaintainService.deleteIfExists(importTaskSetting.getKey());

            assertEquals(
                    0,
                    importTaskMaintainService.lookupAsList(
                            ImportTaskMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{IMPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ImportTask importTask : importTasks) {
                assertTrue(importTaskMaintainService.exists(importTask.getKey()));
                ImportTask testImportTask = importTaskMaintainService.get(importTask.getKey());
                assertNull(testImportTask.getSettingKey());
            }
        } finally {
            for (ImportTask ImportTask : importTasks) {
                if (Objects.isNull(ImportTask.getKey())) {
                    continue;
                }
                importTaskMaintainService.deleteIfExists(ImportTask.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(importTaskSetting.getKey())) {
                importTaskSettingMaintainService.deleteIfExists(importTaskSetting.getKey());
            }
        }
    }

    @Test
    public void testForUserCascade() throws Exception {
        try {
            importTaskSettingMaintainService.insertOrUpdate(importTaskSetting);
            userMaintainService.insertOrUpdate(user);
            for (ImportTask importTask : importTasks) {
                importTask.setKey(importTaskMaintainService.insert(importTask));
            }

            assertEquals(
                    importTasks.size(),
                    importTaskMaintainService.lookupAsList(
                            ImportTaskMaintainService.CHILD_FOR_USER,
                            new Object[]{USER_KEY}
                    ).size()
            );

            userMaintainService.deleteIfExists(user.getKey());

            assertEquals(
                    0,
                    importTaskMaintainService.lookupAsList(
                            ImportTaskMaintainService.CHILD_FOR_USER,
                            new Object[]{USER_KEY}
                    ).size()
            );

            for (ImportTask importTask : importTasks) {
                assertTrue(importTaskMaintainService.exists(importTask.getKey()));
                ImportTask testImportTask = importTaskMaintainService.get(importTask.getKey());
                assertNull(testImportTask.getUserKey());
            }
        } finally {
            for (ImportTask ImportTask : importTasks) {
                if (Objects.isNull(ImportTask.getKey())) {
                    continue;
                }
                importTaskMaintainService.deleteIfExists(ImportTask.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(importTaskSetting.getKey())) {
                importTaskSettingMaintainService.deleteIfExists(importTaskSetting.getKey());
            }
        }
    }
}
