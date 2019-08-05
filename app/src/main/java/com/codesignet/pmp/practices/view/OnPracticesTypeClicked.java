package com.codesignet.pmp.practices.view;

import com.codesignet.pmp.practices.data_access_layer.KnowledgeAreasObject;
import com.codesignet.pmp.practices.data_access_layer.ProcessGroupsObject;

public interface OnPracticesTypeClicked {
    void onProcessGroupsClicked(ProcessGroupsObject object);
    void onKnowledgeAreasClicked(KnowledgeAreasObject object);
}
