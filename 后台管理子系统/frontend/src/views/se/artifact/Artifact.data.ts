import {BasicColumn} from '@/components/Table';
import {FormSchema} from '@/components/Table';
import { rules} from '@/utils/helper/validator';
import { render } from '@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '文物名称',
    align:"center",
    dataIndex: 'artifactname'
   },
   {
    title: '文物名称中文',
    align:"center",
    dataIndex: 'artifactnameChinese'
   },
   {
    title: '国家',
    align:"center",
    dataIndex: 'country'
   },
   {
    title: '文物时代',
    align:"center",
    dataIndex: 'relictime'
   },
   {
    title: '文物时代中文',
    align:"center",
    dataIndex: 'relictimeChinese'
   },
   {
    title: '文物类别',
    align:"center",
    dataIndex: 'material'
   },
   {
    title: '文物类别中文',
    align:"center",
    dataIndex: 'materialChinese'
   },
   {
    title: '文物藏馆',
    align:"center",
    dataIndex: 'library'
   },
   {
    title: '文物藏馆中文',
    align:"center",
    dataIndex: 'libraryChinese'
   },
   {
    title: '文物尺寸',
    align:"center",
    dataIndex: 'size'
   },
   {
    title: '文物尺寸中文',
    align:"center",
    dataIndex: 'sizeChinese'
   },
   {
    title: '文物描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '文物描述中文',
    align:"center",
    dataIndex: 'descriptionChinese'
   },
   {
    title: '文物图片地址',
    align:"center",
    dataIndex: 'moreurl'
   },
   {
    title: '文物原地址',
    align:"center",
    dataIndex: 'imageurl'
   },
   {
    title: '文物时间顺序',
    align:"center",
    dataIndex: 'orderTime'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '文物名称',
    field: 'artifactname',
    component: 'Input',
  },
  {
    label: '文物名称中文',
    field: 'artifactnameChinese',
    component: 'Input',
  },
  {
    label: '国家',
    field: 'country',
    component: 'Input',
  },
  {
    label: '文物时代',
    field: 'relictime',
    component: 'Input',
  },
  {
    label: '文物时代中文',
    field: 'relictimeChinese',
    component: 'Input',
  },
  {
    label: '文物类别',
    field: 'material',
    component: 'Input',
  },
  {
    label: '文物类别中文',
    field: 'materialChinese',
    component: 'Input',
  },
  {
    label: '文物藏馆',
    field: 'library',
    component: 'Input',
  },
  {
    label: '文物藏馆中文',
    field: 'libraryChinese',
    component: 'Input',
  },
  {
    label: '文物尺寸',
    field: 'size',
    component: 'Input',
  },
  {
    label: '文物尺寸中文',
    field: 'sizeChinese',
    component: 'Input',
  },
  {
    label: '文物描述',
    field: 'description',
    component: 'InputTextArea',
  },
  {
    label: '文物描述中文',
    field: 'descriptionChinese',
    component: 'InputTextArea',
  },
  {
    label: '文物图片地址',
    field: 'moreurl',
    component: 'Input',
  },
  {
    label: '文物原地址',
    field: 'imageurl',
    component: 'Input',
  },
  {
    label: '文物时间顺序',
    field: 'orderTime',
    component: 'InputNumber',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  artifactname: {title: '文物名称',order: 0,view: 'text', type: 'string',},
  artifactnameChinese: {title: '文物名称中文',order: 1,view: 'text', type: 'string',},
  country: {title: '国家',order: 2,view: 'text', type: 'string',},
  relictime: {title: '文物时代',order: 3,view: 'text', type: 'string',},
  relictimeChinese: {title: '文物时代中文',order: 4,view: 'text', type: 'string',},
  material: {title: '文物类别',order: 5,view: 'text', type: 'string',},
  materialChinese: {title: '文物类别中文',order: 6,view: 'text', type: 'string',},
  library: {title: '文物藏馆',order: 7,view: 'text', type: 'string',},
  libraryChinese: {title: '文物藏馆中文',order: 8,view: 'text', type: 'string',},
  size: {title: '文物尺寸',order: 9,view: 'text', type: 'string',},
  sizeChinese: {title: '文物尺寸中文',order: 10,view: 'text', type: 'string',},
  description: {title: '文物描述',order: 11,view: 'textarea', type: 'string',},
  descriptionChinese: {title: '文物描述中文',order: 12,view: 'textarea', type: 'string',},
  moreurl: {title: '文物图片地址',order: 13,view: 'text', type: 'string',},
  imageurl: {title: '文物原地址',order: 14,view: 'text', type: 'string',},
  orderTime: {title: '文物时间顺序',order: 15,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
