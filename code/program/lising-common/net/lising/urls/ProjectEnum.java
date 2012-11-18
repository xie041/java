package net.lising.urls;

/**
 * 
 * 工程枚举   配置的返回值和core.properties中的key对应
 * 
 * @author xie041
 * 
 */
public enum ProjectEnum {

	/**
	 * @author xie041
	 *
	 */
	lising_common {
		@Override
		public String getProjectURL() {
			return ProjectUtil.getCommonProjectAddress();
		}

		@Override
		public String getProjectName() {
			return "lising-common";
		}

		@Override
		public boolean hasStaticFiles() {
			return true;
		}
	},
	/**
	 * @author xie041
	 *
	 */
	lising_im {

		@Override
		public String getProjectURL() {
			return "";
		}
		
		@Override
		public String getProjectName() {
			return "lising-im";
		}

		@Override
		public boolean hasStaticFiles() {
			return false;
		}
	}, 
	/**
	 * @author xie041
	 *
	 */
	lising_finance {
		@Override
		public String getProjectURL() {
			return ProjectUtil.getFinanceProjectAddress();
		}

		@Override
		public String getProjectName() {
			return "lising-finance";
		}

		@Override
		public boolean hasStaticFiles() {
			return true;
		}
	}, 
	/**
	 * 
	 * @author xie041
	 *
	 */
	lising_manage {
		@Override
		public String getProjectURL() {
			return ProjectUtil.getManageProjectAddress();
		}

		@Override
		public String getProjectName() {
			return "lising-manage";
		}

		@Override
		public boolean hasStaticFiles() {
			return true;
		}
	}, 
	/**
	 * 计划项目地址
	 * @author xie041
	 *
	 */
	lising_plan {
		@Override
		public String getProjectURL() {
			return ProjectUtil.getPlanProjectAddress();
		}

		@Override
		public String getProjectName() {
			return "lising-plan";
		}

		@Override
		public boolean hasStaticFiles() {
			return true;
		}
	},
	/**
	 * 搜索工程地址
	 * @author xie041
	 *
	 */
	lising_search {
		@Override
		public String getProjectURL() {
			return ProjectUtil.getSearchProjectAddress();
		}

		@Override
		public String getProjectName() {
			return "lising-search";
		}

		@Override
		public boolean hasStaticFiles() {
			return true;
		}
	},
	lising_news {
		@Override
		public String getProjectURL() {
			return ProjectUtil.getSearchProjectAddress();
		}

		@Override
		public String getProjectName() {
			return "lising-news";
		}

		@Override
		public boolean hasStaticFiles() {
			return true;
		}
	},
	/**
	 * sso服务器地址
	 * @author xie041
	 *
	 */
	sso_server {

		@Override
		public String getProjectURL() {
			return ProjectUtil.getSsoProjectAddress();
		}
		
		@Override
		public String getProjectName() {
			return "cas-server";
		}

		@Override
		public boolean hasStaticFiles() {
			return false;
		}
	},
	/**
	 * 缓存服务器地址
	 * @author xie041
	 *
	 */
	mem_server {

		@Override
		public String getProjectURL() {
			return ProjectUtil.getMemcachedAddress();
		}
		
		@Override
		public String getProjectName() {
			return "memcached_server";
		}

		@Override
		public boolean hasStaticFiles() {
			// TODO Auto-generated method stub
			return false;
		}
	},hfs_server {

		@Override
		public String getProjectURL() {
			return ProjectUtil.getHfsAddress();
		}
		
		@Override
		public String getProjectName() {
			return "hfs-server";
		}

		@Override
		public boolean hasStaticFiles() {
			return false;
		}
	};
	
	public abstract String getProjectName();
	public abstract String getProjectURL();
	public abstract boolean hasStaticFiles();
}