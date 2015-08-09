package com.myself.finance.security;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.myself.finance.entity.Permission;
import com.myself.finance.param.PermissionQueryParam;
import com.myself.finance.service.IPermissionService;


public class ChainDefinitionSectionMetaSource implements
		FactoryBean<Ini.Section> {

	@Autowired  
    private IPermissionService permissionService;
	
	private String filterChainDefinitions;

	/**
	 * Ĭ��premission�ַ�
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	/**
	 * ͨ��filterChainDefinitions��Ĭ�ϵ�url���˶���
	 * 
	 * @param filterChainDefinitions
	 *            Ĭ�ϵ�url���˶���
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Section getObject() throws Exception {
		//��ȡ����Resource  
		List<Permission> permissions = permissionService.list(new PermissionQueryParam());
  
        Ini ini = new Ini();  
        //����Ĭ�ϵ�url  
        ini.load(filterChainDefinitions);  
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);  
        //ѭ��Resource��url,�����ӵ�section�С�section����filterChainDefinitionMap,  
        //����ļ��������URL,ֵ���Ǵ���ʲô�������ܷ��ʸ�����  
        if (!CollectionUtils.isEmpty(permissions)) {
			for (Permission permission : permissions) {
				if(StringUtils.isNotEmpty(permission.getUrl()) && StringUtils.isNotEmpty(permission.getName())) {
					section.put(permission.getUrl(),  MessageFormat.format(PREMISSION_STRING, permission.getName()));
				}
			}
		}
		return section;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}

}
