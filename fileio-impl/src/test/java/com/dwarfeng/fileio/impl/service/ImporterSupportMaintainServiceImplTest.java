package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;
import com.dwarfeng.fileio.stack.service.ImporterSupportMaintainService;
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
public class ImporterSupportMaintainServiceImplTest {

    @Autowired
    private ImporterSupportMaintainService service;

    private final List<ImporterSupport> importerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ImporterSupport ImporterSupport = new ImporterSupport(
                    new StringIdKey("driver-support-" + (i + 1)), "label", "description",
                    "exampleParam", "exampleMetadata"
            );
            importerSupports.add(ImporterSupport);
        }
    }

    @After
    public void tearDown() {
        importerSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (ImporterSupport importerSupport : importerSupports) {
                importerSupport.setKey(service.insert(importerSupport));
                service.update(importerSupport);
                ImporterSupport testImporterSupport = service.get(importerSupport.getKey());
                assertEquals(BeanUtils.describe(importerSupport), BeanUtils.describe(testImporterSupport));
            }
        } finally {
            for (ImporterSupport ImporterSupport : importerSupports) {
                if (Objects.isNull(ImporterSupport.getKey())) {
                    continue;
                }
                service.deleteIfExists(ImporterSupport.getKey());
            }
        }
    }
}
