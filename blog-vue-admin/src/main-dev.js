import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import directive from './directive' // directive
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import plugins from './plugins' // plugins
import './assets/icons' // icon
import '@/permission' // permission control
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from '@/utils/sg'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// 全局方法挂载
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.handleTree = handleTree


// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(directive)
Vue.use(ElementUI)
Vue.use(plugins)
Vue.use(mavonEditor)
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
