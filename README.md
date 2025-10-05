# CSU悦动青春Android APP

## 简介

这是CSU悦动青春项目的Android端。

## 采用技术

主要语言：Kotlin

框架：Jetpack Compose

## CI/CD说明

由于GitLab所处的服务器性能有限，无法支持Gradle构建Android程序，我们决定采取以下的CI方式：

1. 在GitLab仓库设置中设置镜像仓库，将GitLab所有分支**自动**推送到GitHub，即每当产生commit就推送。该GitHub仓库的URL是：https://github.com/MitochondriaCN/DynamicYouth-Android

2. 在项目中新建`.github/workflows/android.yml`，当这个文件被推送到GitHub，就会启用GitHub Actions，从而启用一个Android CI。*注意到推送这个文件的一个必要条件是：为GitLab指定的用以登录GitHub仓库的Personal access token，必须带有`workflow`权限。*

3. 项目开发仍然应当在GitLab仓库中进行，最好不要直接提交到GitHub仓库。将GitHub仓库作为一个单纯的镜像来使用。即便需要修改`android.yml`，也应当提交到GitLab仓库。