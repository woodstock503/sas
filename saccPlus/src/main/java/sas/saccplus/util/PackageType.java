package sas.saccplus.util;

public enum PackageType {
	STANDARD("94e94133f4bdc1794c6b647b8ea134d0"), PROFESSIONAL("c2e26e257fdbb435b39cc022429b95b5"), ENTERPRISE("4d4afda25a3f52041ee1b569157130b8"), ALL("5fb1f955b45e38e31789286a1790398d");
	private String key;

	private PackageType(String key) {
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	public static PackageType findByKey(String key){
		for(PackageType v : values()){
			if( v.key.equals(key)){
				return v;
			}
		}
		return null;
	}
};
