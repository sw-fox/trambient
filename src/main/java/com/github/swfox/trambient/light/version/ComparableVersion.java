package com.github.swfox.trambient.light.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparableVersion {
    private final static Logger log = LoggerFactory.getLogger(ComparableVersion.class);
    private final int major;
    private final int minor;
    private final int patch;
    private final String qualifier;

    public ComparableVersion(String version) {
        String[] splitNumbers = version.split("\\.");
        if (splitNumbers.length == 3) {
            major = Integer.parseInt(splitNumbers[0]);
            minor = Integer.parseInt(splitNumbers[1]);
            String[] splitQualifier = splitNumbers[2].split("-");
            patch = Integer.parseInt(splitQualifier[0]);
            if (splitQualifier.length == 2) {
                qualifier = splitQualifier[1];
            }else{
                qualifier="";
            }
        }else{
            major=0;
            minor=0;
            patch=0;
            qualifier="";
            log.error("could not parse version <{}>, no semVer format detected", version);
        }
    }

    public boolean isOlderThan(String version){
        return this.isOlderThan(new ComparableVersion(version));
    }

    public boolean isOlderThan(ComparableVersion compare) {
        Boolean numbers = areNumbersOlderThan(compare);
        if(numbers!= null){
            return numbers;
        }
        return this.qualifier.compareTo(compare.qualifier) < 0;
    }

    private Boolean areNumbersOlderThan(ComparableVersion compare) {
        if(this.major < compare.major){
            return true;
        }else if(this.major > compare.major){
            return false;
        }
        if(this.minor < compare.minor){
            return true;
        }else if(this.minor > compare.minor){
            return false;
        }
        if(this.patch < compare.patch){
            return true;
        }else if(this.patch > compare.patch){
            return false;
        }
        return null;
    }

    public String toString(){
        return major + "." +minor + "." +patch + (qualifier.length()>0?"-":"") +qualifier;
    }

    public boolean isNewerThan(String version){
        return this.isNewerThan(new ComparableVersion(version));
    }

    public boolean isNewerThan(ComparableVersion compare) {
        Boolean numbers = areNumbersOlderThan(compare);
        if(numbers!= null){
            return !numbers;
        }
        return this.qualifier.compareTo(compare.qualifier) > 0;
    }
}
