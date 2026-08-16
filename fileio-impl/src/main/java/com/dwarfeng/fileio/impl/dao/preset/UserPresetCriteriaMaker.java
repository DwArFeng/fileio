package com.dwarfeng.fileio.impl.dao.preset;

import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;

@Component
public class UserPresetCriteriaMaker implements PresetCriteriaMaker {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }
}
