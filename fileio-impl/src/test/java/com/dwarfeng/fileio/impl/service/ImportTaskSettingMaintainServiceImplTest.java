package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
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
public class ImportTaskSettingMaintainServiceImplTest {

    @Autowired
    private ImportTaskSettingMaintainService service;

    private final List<ImportTaskSetting> importTaskSettings = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ImportTaskSetting importTaskSetting = new ImportTaskSetting(null, true, "name", "remark");
            importTaskSettings.add(importTaskSetting);
        }
    }

    @After
    public void tearDown() {
        importTaskSettings.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (ImportTaskSetting importTaskSetting : importTaskSettings) {
                importTaskSetting.setKey(service.insert(importTaskSetting));
                service.update(importTaskSetting);
                ImportTaskSetting testImportTaskSetting = service.get(importTaskSetting.getKey());
                assertEquals(BeanUtils.describe(importTaskSetting), BeanUtils.describe(testImportTaskSetting));
            }
        } finally {
            for (ImportTaskSetting ImportTaskSetting : importTaskSettings) {
                if (Objects.isNull(ImportTaskSetting.getKey())) {
                    continue;
                }
                service.deleteIfExists(ImportTaskSetting.getKey());
            }
        }
    }
}
