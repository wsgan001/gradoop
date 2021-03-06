/**
 * Copyright © 2014 - 2017 Leipzig University (Database Research Group)
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
 */
package org.gradoop.flink.model.impl.operators.matching.transactional.function;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.FunctionAnnotation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.common.model.impl.id.GradoopIdList;

/**
 * Returns one Tuple2<GradoopId, T> per id contained in the first field.
 * @param <T> any type
 */
@FunctionAnnotation.ForwardedFields("f1")
public class ExpandFirstField<T>
  implements FlatMapFunction<Tuple2<GradoopIdList, T>, Tuple2<GradoopId, T>> {

  /**
   * Reduce instantiation
   */
  private Tuple2<GradoopId, T> reuseTuple = new Tuple2<>();

  @Override
  public void flatMap(Tuple2<GradoopIdList, T> tuple2, Collector<Tuple2<GradoopId, T>> collector)
    throws Exception {

    reuseTuple.f1 = tuple2.f1;
    for (GradoopId id : tuple2.f0) {
      reuseTuple.f0 = id;
      collector.collect(reuseTuple);
    }
  }
}
