/*
 * SLD Editor - The Open Source Java SLD Editor
 *
 * Copyright (C) 2016, SCISYS UK Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sldeditor.datasource.impl;

import com.sldeditor.common.SLDDataInterface;
import com.sldeditor.common.data.SLDUtils;
import com.sldeditor.datasource.SLDEditorFileInterface;
import com.sldeditor.datasource.attribute.DataSourceAttributeData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.StyledLayerDescriptor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

/**
 * The Class CreateInternalDataSource.
 *
 * @author Robert Ward (SCISYS)
 */
public class CreateInternalDataSource implements CreateDataSourceInterface {

    /** The data source info. */
    private DataSourceInfo dsInfo = new DataSourceInfo();

    /** The Constant INTERNAL_SCHEMA_NAME. */
    private static final String INTERNAL_SCHEMA_NAME = "MEMORY";

    /** The geometry field. */
    private GeometryField geometryField = new GeometryField();

    /** The list of class considered geometry types. */
    private static List<Class<?>> geometryTypeList =
            Arrays.asList(Geometry.class, MultiPolygon.class, LineString.class, Point.class);

    /**
     * Creates the.
     *
     * @param typeName the type name
     * @param geometryFieldName the geometry field name
     * @param editorFile the editor file
     * @return the list of datastores
     */
    @Override
    public List<DataSourceInfo> connect(
            String typeName, String geometryFieldName, SLDEditorFileInterface editorFile) {
        List<DataSourceInfo> dataSourceInfoList = new ArrayList<>();
        dataSourceInfoList.add(dsInfo);

        dsInfo.reset();

        if (editorFile == null) {
            return dataSourceInfoList;
        }

        StyledLayerDescriptor sld = editorFile.getSLD();

        determineGeometryType(sld);

        ExtendedSimpleFeatureTypeBuilder b = new ExtendedSimpleFeatureTypeBuilder();

        // set the type name
        String internalTypeName = INTERNAL_SCHEMA_NAME;
        dsInfo.setTypeName(internalTypeName);
        b.setName(internalTypeName);

        String namespace = null;
        b.setNamespaceURI(namespace);

        // add a geometry property
        b.setCRS(DefaultGeographicCRS.WGS84); // set crs first

        SLDDataInterface sldData = editorFile.getSLDData();
        List<DataSourceAttributeData> fieldList = sldData.getFieldList();

        // Set the geometry field by default
        geometryField.reset();
        if (geometryFieldName != null) {
            geometryField.setGeometryFieldName(geometryFieldName);
        }

        List<AttributeDescriptor> attrDescList = new ArrayList<>();

        if ((fieldList == null) || fieldList.isEmpty()) {
            // Read the fields from the SLD
            ExtractAttributes extract = new ExtractAttributes();
            extract.extractDefaultFields(sld);
            fieldList = extract.getFields();

            // Add non-geometry fields to the feature type builder
            for (DataSourceAttributeData dsAttribute : fieldList) {
                if (dsAttribute.getName() != null) {
                    b.add(dsAttribute.getName(), dsAttribute.getType());
                }
            }

            List<String> geometryFields = extract.getGeometryFields();
            for (String localGeometryFieldName : geometryFields) {
                geometryField.setGeometryFieldName(localGeometryFieldName);
            }
        } else {
            addFields(attrDescList, b, fieldList);
        }
        attrDescList.add(addGeometryField(b, geometryField.getGeometryFieldName()));

        b.addAll(attrDescList);

        // Store the fields
        sldData.setFieldList(fieldList);

        // Build the feature type
        SimpleFeatureType schema = b.buildFeatureType();
        dsInfo.setSchema(schema);

        CreateSampleData sampleData = new CreateSampleData();
        sampleData.create(schema, fieldList);
        MemoryDataStore dataStore = sampleData.getDataStore();

        dsInfo.setDataStore(dataStore);

        dsInfo.populateFieldMap();

        return dataSourceInfoList;
    }

    /**
     * Adds the fields.
     *
     * @param attrDescList the attribute descriptor list
     * @param b the feature type builder
     * @param fieldList the field list
     */
    private void addFields(
            List<AttributeDescriptor> attrDescList,
            ExtendedSimpleFeatureTypeBuilder b,
            List<DataSourceAttributeData> fieldList) {

        for (DataSourceAttributeData field : fieldList) {
            Class<?> fieldType = field.getType();
            if (!isGeometryField(field.getType())) {
                AttributeDescriptor attributeDescriptor =
                        b.createAttributeDescriptor(field.getName(), fieldType);
                attrDescList.add(attributeDescriptor);
            }
        }
    }

    /**
     * Adds the geometry field.
     *
     * @param b the b
     * @param fieldName the field name
     * @return the attribute descriptor
     */
    private AttributeDescriptor addGeometryField(
            ExtendedSimpleFeatureTypeBuilder b, String fieldName) {
        geometryField.setGeometryFieldName(fieldName);
        Class<?> fieldType;
        switch (dsInfo.getGeometryType()) {
            case POLYGON:
                fieldType = MultiPolygon.class;
                break;
            case LINE:
                fieldType = LineString.class;
                break;
            case POINT:
            default:
                fieldType = Point.class;
                break;
        }
        b.setDefaultGeometry(fieldName);
        return b.createAttributeDescriptor(fieldName, fieldType);
    }

    /**
     * Checks if field is a geometry field.
     *
     * @param fieldType the field type
     * @return true, if is geometry field
     */
    private boolean isGeometryField(Class<?> fieldType) {
        return geometryTypeList.contains(fieldType);
    }

    /**
     * Determine geometry type.
     *
     * @param sld the sld
     */
    private void determineGeometryType(StyledLayerDescriptor sld) {
        GeometryTypeEnum geometryType = internalDetermineGeometryType(sld);

        dsInfo.setGeometryType(geometryType);
    }

    /**
     * Internal determine geometry type.
     *
     * @param sld the sld
     * @return the geometry type enum
     */
    protected GeometryTypeEnum internalDetermineGeometryType(StyledLayerDescriptor sld) {
        GeometryTypeEnum geometryType = GeometryTypeEnum.UNKNOWN;

        SymbolizerCount symbolizerCount = new SymbolizerCount();

        if (sld != null) {
            SLDUtils.traverseSymbolizers(sld, symbolizerCount);
        }

        if (symbolizerCount.getPolygonSymbolizerCount() > 0) {
            geometryType = GeometryTypeEnum.POLYGON;
        } else if (symbolizerCount.getLineSymbolizerCount() > 0) {
            geometryType = GeometryTypeEnum.LINE;
        } else if (symbolizerCount.getPointSymbolizerCount() > 0) {
            geometryType = GeometryTypeEnum.POINT;
        } else if (symbolizerCount.getRasterSymbolizerCount() > 0) {
            geometryType = GeometryTypeEnum.RASTER;
        }

        return geometryType;
    }
}
