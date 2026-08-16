package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.WriterSupport;
import com.dwarfeng.fileio.stack.service.WriterSupportMaintainService;
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
public class WriterSupportMaintainServiceImplTest {

    @Autowired
    private WriterSupportMaintainService service;

    private final List<WriterSupport> writerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            WriterSupport writerSupport = new WriterSupport(
                    new StringIdKey("driver-support-" + (i + 1)), "label", "description",
                    "exampleParam", "exampleMetadata"
            );
            writerSupports.add(writerSupport);
        }
    }

    @After
    public void tearDown() {
        writerSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (WriterSupport writerSupport : writerSupports) {
                writerSupport.setKey(service.insert(writerSupport));
                service.update(writerSupport);
                WriterSupport testWriterSupport = service.get(writerSupport.getKey());
                assertEquals(BeanUtils.describe(writerSupport), BeanUtils.describe(testWriterSupport));
            }
        } finally {
            for (WriterSupport WriterSupport : writerSupports) {
                if (Objects.isNull(WriterSupport.getKey())) {
                    continue;
                }
                service.deleteIfExists(WriterSupport.getKey());
            }
        }
    }
}
