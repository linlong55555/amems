package com.eray.pbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.MaterialShortageDao;
import com.eray.pbs.dao.MaterialShortagePbsDao;
import com.eray.pbs.po.MaterialShortage;
import com.eray.pbs.po.MaterialShortagePbs;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class MaterialShortageService
{
	private static final Logger logger = LoggerFactory.getLogger(MaterialShortageService.class);
    @Autowired
    private MaterialShortageDao materialShortageDao;
    @Autowired
    private MaterialShortagePbsDao materialShortagePbsDao;
    @Autowired
	private JdbcTemplate jdbcTemplate;
    
    private MaterialShortagePbs  materialShortagePbs;
    
    private Map<String, Object> searchParams= new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<MaterialShortage> specMaterialShortage;
    private PageRequest pageRequest;
    private Page<MaterialShortage> page;
    
    private Specification<MaterialShortagePbs> specMaterialShortagePbs;
    private List<MaterialShortagePbs> materialShortagePbsList;
    
    @Transactional(readOnly = false)
    public MaterialShortage save(MaterialShortage materialShortage , String[] para)
    {
        if(materialShortage==null || para==null ||para.length<22){
            return materialShortage;
        }
        
        searchParams.clear();
        searchParams.put("EQ_workOrder", materialShortage.getWorkOrder());
        searchParams.put("EQ_materialNumber", materialShortage.getMaterialNumber());
        searchParams.put("EQ_mrCreationDate", materialShortage.getMrCreationDate());
        filters = SearchFilter.parse(searchParams);
        specMaterialShortagePbs = DynamicSpecifications.bySearchFilter(filters.values(), MaterialShortagePbs.class);
        materialShortagePbsList = materialShortagePbsDao.findAll(specMaterialShortagePbs);
        if (materialShortagePbsList != null && materialShortagePbsList.size()>0)
        {
            materialShortagePbs=materialShortagePbsList.get(0);
        }else{
            materialShortagePbs = new MaterialShortagePbs();
            materialShortagePbs.setWorkOrder(materialShortage.getWorkOrder());
            materialShortagePbs.setMaterialNumber(materialShortage.getMaterialNumber());
            materialShortagePbs.setMrCreationDate(materialShortage.getMrCreationDate());
        }
        materialShortagePbs.setCardNumber(para[0]);
        materialShortagePbs.setWorkOrderDescription(para[2]);
        materialShortagePbs.setMaterialDescription(para[4]);
        materialShortagePbs.setRequiredQuantity(para[5]);
        materialShortagePbs.setMrNumber(para[6]);
        materialShortagePbs.setPriority(para[8]);
        materialShortagePbs.setRequiredDeliveryDate(para[9]);
        materialShortagePbs.setStatus(para[10]);
        materialShortagePbs.setEstimatedGrDate(para[11]);
        materialShortagePbs.setWorkCenter(para[12]);
        materialShortagePbs.setCreatedBy(para[13]);
        materialShortagePbs.setRemark(para[14]);
        materialShortagePbs.setMaterialGroup(para[15]);
        materialShortagePbs.setCfmFlag(para[16]);
        materialShortagePbs.setSpecialStock(para[17]);
        materialShortagePbs.setPoNumber(para[18]);
        materialShortagePbs.setIncotermsDescription(para[19]);
        materialShortagePbs.setPoDepartureDate(para[20]);
        materialShortagePbs.setAwb(para[21]);
        if(para.length>23){
            materialShortagePbs.setEtdDepartureDate(para[22]);
            materialShortagePbs.setEtaArrivalDate(para[23]);
        }
        materialShortagePbs.setIsLast(1);
        materialShortagePbs = materialShortagePbsDao.save(materialShortagePbs);
        materialShortage = materialShortageDao.save(materialShortage);
        materialShortage.setPbsMaterialShortageId(materialShortagePbs.getId());//pbsMaterialShortageId非MaterialShortage对应数据库字段
        return materialShortage;
    }

    public MaterialShortage findLast(String workOrder, String materialNumber, String mrCreationDate)
    {
        if(workOrder==null ||materialNumber==null||mrCreationDate==null||workOrder.equals("")||materialNumber.equals("")||mrCreationDate.equals("")){
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_workOrder", workOrder);
        searchParams.put("EQ_materialNumber", materialNumber);
        searchParams.put("EQ_mrCreationDate", mrCreationDate);
        filters = SearchFilter.parse(searchParams);
        specMaterialShortage = DynamicSpecifications.bySearchFilter(filters.values(),MaterialShortage.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page=materialShortageDao.findAll(specMaterialShortage, pageRequest);
        if(page.getContent()!=null && page.getContent().size()>0){
            return page.getContent().get(0);
        }
        return null;
    }

    @Transactional(readOnly = false)
	public int resetIsLast(int islast)
    {
    	return materialShortagePbsDao.resetIsLast(islast);
    }

	public MaterialShortagePbs findPbsById(Long id)
    {
	    return materialShortagePbsDao.findOne(id);
    }

	@Transactional(readOnly = false)
	public MaterialShortagePbs savePbs(MaterialShortagePbs materialShortagePbs)
    {
		return materialShortagePbsDao.save(materialShortagePbs);
    }
	
	/**
	 * 将不缺航材的记录更新为0:已到
	 * @param materialShortageIds 缺件的ID
	 */
	@Transactional(readOnly = false)
	public void updateMaterialToZero(List<Long> materialShortageIds) {
		if(materialShortageIds != null && materialShortageIds.size() > 0){
			
			//全部置0
			jdbcTemplate.execute(" update pbs_materialshortage set islast_ = '0'");
		
			StringBuffer bf = new StringBuffer();

			int nums = 0;
			for(Long id : materialShortageIds) 
			{
				nums++;
				bf.append(","+id);
				
				//分批次更新，一次900条
				if(nums == 900)
				{
					jdbcTemplate.execute(" update pbs_materialshortage set islast_ = '1' where id_  in("+bf.toString().replaceFirst(",", "")+")");
					logger.info("157->materialShortageIds:{}", bf.toString());
					bf.setLength(0);
					nums = 0;
				}
			}

			//将剩余的全部更新
			if(bf != null && bf.toString().length() > 0)
			{
				jdbcTemplate.execute(" update pbs_materialshortage set islast_ = '1' where id_ in("+bf.toString().replaceFirst(",", "")+")");
				logger.info("167->materialShortageIds:{}", bf.toString());
				bf = null;
				nums = 0;
			}
		}
	}
}
