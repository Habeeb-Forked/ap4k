/**
 * Copyright 2018 The original authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
**/
package io.ap4k.kubernetes.annotation;

public @interface GitRepoVolume {

  /**
   * The volumeName name.
   * @return  The volumeName name.
   */
  String volumeName();

  /**
   * Git repoistory URL.
   * @return  The url of the repository.
   */
  String repository();

  /**
   * The directory of the repository to mount.
   * @return  The relative path to the directory.
   */
  String directory() default "";


  /**
   * The commit hash to use.
   * @return  The hash, or empty if the head of the repo (default).
   */
  String revision() default "";
}
