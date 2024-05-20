import { BasicColumn } from '@/components/Table';
import { FormSchema } from '@/components/Table';
import { rules } from '@/utils/helper/validator';
import { render } from '@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '用户昵称',
    align: 'center',
    dataIndex: 'username',
  },
  // {
  //   title: '密码',
  //   align: 'center',
  //   dataIndex: 'password',
  // },
  {
    title: '头像URL',
    align: 'center',
    dataIndex: 'avatarUrl',
  },
  {
    title: '电子邮件地址',
    align: 'center',
    dataIndex: 'email',
  },
  {
    title: '手机号码',
    align: 'center',
    dataIndex: 'phone',
  },
  {
    title: 'isbanned',
    align: 'center',
    dataIndex: 'isbanned',
  },
  {
    title: 'sex',
    align: 'center',
    dataIndex: 'sex',
  },
  {
    title: 'age',
    align: 'center',
    dataIndex: 'age',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '用户昵称',
    field: 'username',
    component: 'Input',
  },
  {
    label: '密码',
    field: 'password',
    component: 'Input',
  },
  {
    label: '头像URL',
    field: 'avatarUrl',
    component: 'Input',
  },
  {
    label: '电子邮件地址',
    field: 'email',
    component: 'Input',
  },
  {
    label: '手机号码',
    field: 'phone',
    component: 'Input',
  },
  {
    label: 'isbanned',
    field: 'isbanned',
    component: 'InputNumber',
  },
  {
    label: 'sex',
    field: 'sex',
    component: 'Input',
  },
  {
    label: 'age',
    field: 'age',
    component: 'Input',
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

// 高级查询数据
export const superQuerySchema = {
  username: { title: '用户昵称', order: 0, view: 'text', type: 'string' },
  password: { title: '密码', order: 1, view: 'text', type: 'string' },
  avatarUrl: { title: '头像URL', order: 2, view: 'text', type: 'string' },
  email: { title: '电子邮件地址', order: 3, view: 'text', type: 'string' },
  phone: { title: '手机号码', order: 4, view: 'text', type: 'string' },
  isbanned: { title: 'isbanned', order: 5, view: 'number', type: 'number' },
  sex: { title: 'sex', order: 6, view: 'text', type: 'string' },
  age: { title: 'age', order: 7, view: 'text', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
