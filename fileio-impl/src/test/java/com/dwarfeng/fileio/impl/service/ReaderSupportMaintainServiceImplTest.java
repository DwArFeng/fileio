package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.entity.ReaderSupport;
import com.dwarfeng.fileio.stack.service.ReaderSupportMaintainService;
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
public class ReaderSupportMaintainServiceImplTest {

    @Autowired
    private ReaderSupportMaintainService service;

    private final List<ReaderSupport> readerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ReaderSupport readerSupport = new ReaderSupport(
                    new StringIdKey("driver-support-" + (i + 1)), "label", "description",
                    "exampleParam", "exampleMetadata"
            );
            readerSupports.add(readerSupport);
        }
    }

    @After
    public void tearDown() {
        readerSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (ReaderSupport readerSupport : readerSupports) {
                readerSupport.setKey(service.insert(readerSupport));
                service.update(readerSupport);
                ReaderSupport testReaderSupport = service.get(readerSupport.getKey());
                assertEquals(BeanUtils.describe(readerSupport), BeanUtils.describe(testReaderSupport));
            }
        } finally {
            for (ReaderSupport ReaderSupport : readerSupports) {
                if (Objects.isNull(ReaderSupport.getKey())) {
                    continue;
                }
                service.deleteIfExists(ReaderSupport.getKey());
            }
        }
    }
}
