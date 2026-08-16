package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ExporterSupport;
import com.dwarfeng.fileio.stack.service.ExporterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
public class ExporterSupportMaintainServiceImplTest {

    @Autowired
    private ExporterSupportMaintainService service;

    private final List<ExporterSupport> exporterSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ExporterSupport ExporterSupport = new ExporterSupport(
                    new StringIdKey("driver-support-" + (i + 1)), "label", "description",
                    "exampleParam", "exampleMetadata"
            );
            exporterSupports.add(ExporterSupport);
        }
    }

    @After
    public void tearDown() {
        exporterSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (ExporterSupport exporterSupport : exporterSupports) {
                exporterSupport.setKey(service.insert(exporterSupport));
                service.update(exporterSupport);
                ExporterSupport testExporterSupport = service.get(exporterSupport.getKey());
                assertEquals(BeanUtils.describe(exporterSupport), BeanUtils.describe(testExporterSupport));
            }
        } finally {
            for (ExporterSupport ExporterSupport : exporterSupports) {
                if (Objects.isNull(ExporterSupport.getKey())) {
                    continue;
                }
                service.deleteIfExists(ExporterSupport.getKey());
            }
        }
    }
}
