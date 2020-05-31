#define _GNU_SOURCE
#include <dlfcn.h>
#include <stdio.h>
#include <sys/stat.h>
#include <string.h>
#include <fcntl.h>


typedef int (*orig_open_f_type)(const char *pathname, int flags);
typedef int (*orig_stat_f_type)(const char *file_name, struct stat *buf);
typedef int (*orig_stat64_f_type)(const char *filename, struct stat64 *buf);
typedef int (*orig_fstatat_f_type)(int dirfd, const char *pathname, struct stat *buf,int flags);

static void changePath(const char* originPath, char *changed);
static int isBeginWith(const char *str1, char *str2);


#define MAXPATH 80

int open(const char *pathname, int flags, ...)
{
    char changed[MAXPATH];
    changePath(pathname, changed);
    printf("open %s ==> %s\n",pathname, changed);
    orig_open_f_type orig_open;
    orig_open = (orig_open_f_type)dlsym(RTLD_NEXT,"open");
    return orig_open(changed,flags);
}

int fstatat(int dirfd, const char *file_name, struct stat *buf,
            int flags){

    char changed[MAXPATH];
    changePath(file_name, changed);
    printf("fstatat %s ==> %s\n",file_name, changed);

    orig_fstatat_f_type orig_fstatat;
    orig_fstatat = (orig_fstatat_f_type)dlsym(RTLD_NEXT,"fstatat");
    return orig_fstatat(dirfd, file_name, buf, flags);            
}


int stat(const char *file_name, struct stat *buf){
    
    char changed[MAXPATH];
    changePath(file_name, changed);
    printf("stat %s ==> %s\n",file_name, changed);

    orig_stat_f_type orig_stat;
    orig_stat = (orig_stat_f_type)dlsym(RTLD_NEXT,"stat");
    return orig_schintat(changed, buf);
}



int stat64 (const char *file_name, struct stat64 *buf){
    char changed[MAXPATH];
    changePath(file_name, changed);
    printf("stat64 %s ==> %s\n",file_name, changed);

    orig_stat64_f_type orig_stat64;
    orig_stat64 = (orig_stat64_f_type)dlsym(RTLD_NEXT,"stat64");
    return orig_stat64(changed, buf);
}


static void changePath(const char* originPath, char* changed){
    if(isBeginWith(originPath, "/tmp")){
       strcpy(changed,  "/tmp");
       strcat(changed, originPath);
    }else {
        strcpy(changed, originPath);
    }
}


static int isBeginWith(const char *str1, char *str2)
{
  if(str1 == NULL || str2 == NULL)
    return -1;
  int len1 = strlen(str1);
  int len2 = strlen(str2);
  if((len1 < len2) || (len1 == 0 || len2 == 0))
    return 0;
  char *p = str2;
  int i = 0;
  while(*p != '\0')
  {
    if(*p != str1[i])
      return 0;
    p++;
    i++;
  }
  return 1;
}

