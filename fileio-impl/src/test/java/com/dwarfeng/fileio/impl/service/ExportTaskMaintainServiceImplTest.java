package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.User;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
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
public class ExportTaskMaintainServiceImplTest {

    private static final LongIdKey EXPORT_TASK_SETTING_KEY = new LongIdKey(12450L);
    private static final StringIdKey USER_KEY = new StringIdKey("test_user");

    @Autowired
    private ExportTaskSettingMaintainService exportTaskSettingMaintainService;
    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private ExportTaskMaintainService exportTaskMaintainService;

    private ExportTaskSetting exportTaskSetting;
    private User user;
    private final List<ExportTask> exportTasks = new ArrayList<>();

    @Before
    public void setUp() {
        exportTaskSetting = new ExportTaskSetting(EXPORT_TASK_SETTING_KEY, true, "name", "remark");
        user = new User(USER_KEY, "remark");
        for (int i = 0; i < 5; i++) {
            ExportTask ExportTask = new ExportTask(
                    null, EXPORT_TASK_SETTING_KEY, USER_KEY, 12450, new Date(), new Date(), new Date(), 12450L, 12450,
                    "message", new Date(), new Date()
            );
            exportTasks.add(ExportTask);
        }
    }

    @After
    public void tearDown() {
        exportTaskSetting = null;
        exportTasks.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            userMaintainService.insertOrUpdate(user);
            for (ExportTask exportTask : exportTasks) {
                exportTask.setKey(exportTaskMaintainService.insert(exportTask));
                exportTaskMaintainService.update(exportTask);
                ExportTask testExportTask = exportTaskMaintainService.get(exportTask.getKey());
                assertEquals(BeanUtils.describe(exportTask), BeanUtils.describe(testExportTask));
            }
        } finally {
            for (ExportTask ExportTask : exportTasks) {
                if (Objects.isNull(ExportTask.getKey())) {
                    continue;
                }
                exportTaskMaintainService.deleteIfExists(ExportTask.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(exportTaskSetting.getKey())) {
                exportTaskSettingMaintainService.deleteIfExists(exportTaskSetting.getKey());
            }
        }
    }

    @Test
    public void testForTaskSettingCascade() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            userMaintainService.insertOrUpdate(user);
            for (ExportTask exportTask : exportTasks) {
                exportTask.setKey(exportTaskMaintainService.insert(exportTask));
            }

            assertEquals(
                    exportTasks.size(),
                    exportTaskMaintainService.lookupAsList(
                            ExportTaskMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            exportTaskSettingMaintainService.deleteIfExists(exportTaskSetting.getKey());

            assertEquals(
                    0,
                    exportTaskMaintainService.lookupAsList(
                            ExportTaskMaintainService.CHILD_FOR_TASK_SETTING,
                            new Object[]{EXPORT_TASK_SETTING_KEY}
                    ).size()
            );

            for (ExportTask exportTask : exportTasks) {
                assertTrue(exportTaskMaintainService.exists(exportTask.getKey()));
                ExportTask testExportTask = exportTaskMaintainService.get(exportTask.getKey());
                assertNull(testExportTask.getSettingKey());
            }
        } finally {
            for (ExportTask ExportTask : exportTasks) {
                if (Objects.isNull(ExportTask.getKey())) {
                    continue;
                }
                exportTaskMaintainService.deleteIfExists(ExportTask.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(exportTaskSetting.getKey())) {
                exportTaskSettingMaintainService.deleteIfExists(exportTaskSetting.getKey());
            }
        }
    }

    @Test
    public void testForUserCascade() throws Exception {
        try {
            exportTaskSettingMaintainService.insertOrUpdate(exportTaskSetting);
            userMaintainService.insertOrUpdate(user);
            for (ExportTask exportTask : exportTasks) {
                exportTask.setKey(exportTaskMaintainService.insert(exportTask));
            }

            assertEquals(
                    exportTasks.size(),
                    exportTaskMaintainService.lookupAsList(
                            ExportTaskMaintainService.CHILD_FOR_USER,
                            new Object[]{USER_KEY}
                    ).size()
            );

            userMaintainService.deleteIfExists(user.getKey());

            assertEquals(
                    0,
                    exportTaskMaintainService.lookupAsList(
                            ExportTaskMaintainService.CHILD_FOR_USER,
                            new Object[]{USER_KEY}
                    ).size()
            );

            for (ExportTask exportTask : exportTasks) {
                assertTrue(exportTaskMaintainService.exists(exportTask.getKey()));
                ExportTask testExportTask = exportTaskMaintainService.get(exportTask.getKey());
                assertNull(testExportTask.getUserKey());
            }
        } finally {
            for (ExportTask ExportTask : exportTasks) {
                if (Objects.isNull(ExportTask.getKey())) {
                    continue;
                }
                exportTaskMaintainService.deleteIfExists(ExportTask.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(exportTaskSetting.getKey())) {
                exportTaskSettingMaintainService.deleteIfExists(exportTaskSetting.getKey());
            }
        }
    }
}
