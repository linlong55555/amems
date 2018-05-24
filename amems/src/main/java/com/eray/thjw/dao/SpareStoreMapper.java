package com.eray.thjw.dao;

import com.eray.thjw.po.SpareStore;
import java.util.List;

public interface SpareStoreMapper {

    List<SpareStore> selectSpareStoreList(SpareStore ss);  
    
}