import {BasicColumn} from '@/components/Table';
import {FormSchema} from '@/components/Table';
import { rules} from '@/utils/helper/validator';
import { render } from '@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: 'userId',
    align:"center",
    dataIndex: 'userId'
   },
   {
    title: 'artifactId',
    align:"center",
    dataIndex: 'artifactId'
   },
   {
    title: '评论内容',
    align:"center",
    dataIndex: 'content'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: 'userId',
    field: 'userId',
    component: 'InputNumber',
  },
  {
    label: 'artifactId',
    field: 'artifactId',
    component: 'InputNumber',
  },
  {
    label: '评论内容',
    field: 'content',
    component: 'InputTextArea',
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
  userId: {title: 'userId',order: 0,view: 'number', type: 'number',},
  artifactId: {title: 'artifactId',order: 1,view: 'number', type: 'number',},
  content: {title: '评论内容',order: 2,view: 'textarea', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
