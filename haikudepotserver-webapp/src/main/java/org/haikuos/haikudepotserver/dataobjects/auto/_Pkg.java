package org.haikuos.haikudepotserver.dataobjects.auto;

import java.util.Date;
import java.util.List;

import org.haikuos.haikudepotserver.dataobjects.PermissionUserPkg;
import org.haikuos.haikudepotserver.dataobjects.PkgIcon;
import org.haikuos.haikudepotserver.dataobjects.PkgLocalization;
import org.haikuos.haikudepotserver.dataobjects.PkgPkgCategory;
import org.haikuos.haikudepotserver.dataobjects.PkgScreenshot;
import org.haikuos.haikudepotserver.dataobjects.Prominence;
import org.haikuos.haikudepotserver.dataobjects.Publisher;
import org.haikuos.haikudepotserver.dataobjects.support.AbstractDataObject;

/**
 * Class _Pkg was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Pkg extends AbstractDataObject {

    public static final String ACTIVE_PROPERTY = "active";
    public static final String CREATE_TIMESTAMP_PROPERTY = "createTimestamp";
    public static final String DERIVED_RATING_PROPERTY = "derivedRating";
    public static final String DERIVED_RATING_SAMPLE_SIZE_PROPERTY = "derivedRatingSampleSize";
    public static final String MODIFY_TIMESTAMP_PROPERTY = "modifyTimestamp";
    public static final String NAME_PROPERTY = "name";
    public static final String PERMISSION_USER_PKGS_PROPERTY = "permissionUserPkgs";
    public static final String PKG_ICONS_PROPERTY = "pkgIcons";
    public static final String PKG_LOCALIZATIONS_PROPERTY = "pkgLocalizations";
    public static final String PKG_PKG_CATEGORIES_PROPERTY = "pkgPkgCategories";
    public static final String PKG_SCREENSHOTS_PROPERTY = "pkgScreenshots";
    public static final String PROMINENCE_PROPERTY = "prominence";
    public static final String PUBLISHER_PROPERTY = "publisher";

    public static final String ID_PK_COLUMN = "id";

    public void setActive(Boolean active) {
        writeProperty(ACTIVE_PROPERTY, active);
    }
    public Boolean getActive() {
        return (Boolean)readProperty(ACTIVE_PROPERTY);
    }

    public void setCreateTimestamp(Date createTimestamp) {
        writeProperty(CREATE_TIMESTAMP_PROPERTY, createTimestamp);
    }
    public Date getCreateTimestamp() {
        return (Date)readProperty(CREATE_TIMESTAMP_PROPERTY);
    }

    public void setDerivedRating(Float derivedRating) {
        writeProperty(DERIVED_RATING_PROPERTY, derivedRating);
    }
    public Float getDerivedRating() {
        return (Float)readProperty(DERIVED_RATING_PROPERTY);
    }

    public void setDerivedRatingSampleSize(Integer derivedRatingSampleSize) {
        writeProperty(DERIVED_RATING_SAMPLE_SIZE_PROPERTY, derivedRatingSampleSize);
    }
    public Integer getDerivedRatingSampleSize() {
        return (Integer)readProperty(DERIVED_RATING_SAMPLE_SIZE_PROPERTY);
    }

    public void setModifyTimestamp(Date modifyTimestamp) {
        writeProperty(MODIFY_TIMESTAMP_PROPERTY, modifyTimestamp);
    }
    public Date getModifyTimestamp() {
        return (Date)readProperty(MODIFY_TIMESTAMP_PROPERTY);
    }

    public void setName(String name) {
        writeProperty(NAME_PROPERTY, name);
    }
    public String getName() {
        return (String)readProperty(NAME_PROPERTY);
    }

    public void addToPermissionUserPkgs(PermissionUserPkg obj) {
        addToManyTarget(PERMISSION_USER_PKGS_PROPERTY, obj, true);
    }
    public void removeFromPermissionUserPkgs(PermissionUserPkg obj) {
        removeToManyTarget(PERMISSION_USER_PKGS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<PermissionUserPkg> getPermissionUserPkgs() {
        return (List<PermissionUserPkg>)readProperty(PERMISSION_USER_PKGS_PROPERTY);
    }


    public void addToPkgIcons(PkgIcon obj) {
        addToManyTarget(PKG_ICONS_PROPERTY, obj, true);
    }
    public void removeFromPkgIcons(PkgIcon obj) {
        removeToManyTarget(PKG_ICONS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<PkgIcon> getPkgIcons() {
        return (List<PkgIcon>)readProperty(PKG_ICONS_PROPERTY);
    }


    public void addToPkgLocalizations(PkgLocalization obj) {
        addToManyTarget(PKG_LOCALIZATIONS_PROPERTY, obj, true);
    }
    public void removeFromPkgLocalizations(PkgLocalization obj) {
        removeToManyTarget(PKG_LOCALIZATIONS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<PkgLocalization> getPkgLocalizations() {
        return (List<PkgLocalization>)readProperty(PKG_LOCALIZATIONS_PROPERTY);
    }


    public void addToPkgPkgCategories(PkgPkgCategory obj) {
        addToManyTarget(PKG_PKG_CATEGORIES_PROPERTY, obj, true);
    }
    public void removeFromPkgPkgCategories(PkgPkgCategory obj) {
        removeToManyTarget(PKG_PKG_CATEGORIES_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<PkgPkgCategory> getPkgPkgCategories() {
        return (List<PkgPkgCategory>)readProperty(PKG_PKG_CATEGORIES_PROPERTY);
    }


    public void addToPkgScreenshots(PkgScreenshot obj) {
        addToManyTarget(PKG_SCREENSHOTS_PROPERTY, obj, true);
    }
    public void removeFromPkgScreenshots(PkgScreenshot obj) {
        removeToManyTarget(PKG_SCREENSHOTS_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<PkgScreenshot> getPkgScreenshots() {
        return (List<PkgScreenshot>)readProperty(PKG_SCREENSHOTS_PROPERTY);
    }


    public void setProminence(Prominence prominence) {
        setToOneTarget(PROMINENCE_PROPERTY, prominence, true);
    }

    public Prominence getProminence() {
        return (Prominence)readProperty(PROMINENCE_PROPERTY);
    }


    public void setPublisher(Publisher publisher) {
        setToOneTarget(PUBLISHER_PROPERTY, publisher, true);
    }

    public Publisher getPublisher() {
        return (Publisher)readProperty(PUBLISHER_PROPERTY);
    }


}
