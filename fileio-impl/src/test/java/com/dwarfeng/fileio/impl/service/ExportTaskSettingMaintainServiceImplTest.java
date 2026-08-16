package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ExportTaskSettingMaintainServiceImplTest {

    @Autowired
    private ExportTaskSettingMaintainService service;

    private final List<ExportTaskSetting> exportTaskSettings = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ExportTaskSetting exportTaskSetting = new ExportTaskSetting(null, true, "name", "remark");
            exportTaskSettings.add(exportTaskSetting);
        }
    }

    @After
    public void tearDown() {
        exportTaskSettings.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (ExportTaskSetting exportTaskSetting : exportTaskSettings) {
                exportTaskSetting.setKey(service.insert(exportTaskSetting));
                service.update(exportTaskSetting);
                ExportTaskSetting testExportTaskSetting = service.get(exportTaskSetting.getKey());
                assertEquals(BeanUtils.describe(exportTaskSetting), BeanUtils.describe(testExportTaskSetting));
            }
        } finally {
            for (ExportTaskSetting ExportTaskSetting : exportTaskSettings) {
                if (Objects.isNull(ExportTaskSetting.getKey())) {
                    continue;
                }
                service.deleteIfExists(ExportTaskSetting.getKey());
            }
        }
    }
}
