import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '是否自动执行(否0, 是1)',
    align: 'center',
    dataIndex: 'isAuto',
  },
  {
    title: '是否成功(否0, 是1)',
    align: 'center',
    dataIndex: 'isSuccess',
  },
  {
    title: '操作类型',
    align: 'center',
    dataIndex: 'operateType',
  },
  {
    title: '日志内容',
    align: 'center',
    dataIndex: 'logContent',
  },
  {
    title: '耗时',
    align: 'center',
    dataIndex: 'costTime',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '是否自动执行(否0, 是1)',
    field: 'isAuto',
    component: 'InputNumber',
  },
  {
    label: '是否成功(否0, 是1)',
    field: 'isSuccess',
    component: 'InputNumber',
  },
  {
    label: '操作类型',
    field: 'operateType',
    component: 'InputNumber',
  },
  {
    label: '日志内容',
    field: 'logContent',
    component: 'InputTextArea',
  },
  {
    label: '耗时',
    field: 'costTime',
    component: 'InputNumber',
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
  isAuto: { title: '是否自动执行(否0, 是1)', order: 0, view: 'number', type: 'number' },
  isSuccess: { title: '是否成功(否0, 是1)', order: 1, view: 'number', type: 'number' },
  operateType: { title: '操作类型(备份0, 还原1)', order: 2, view: 'number', type: 'number' },
  logContent: { title: '日志内容', order: 3, view: 'text', type: 'string' },
  costTime: { title: '耗时', order: 4, view: 'number', type: 'number' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
